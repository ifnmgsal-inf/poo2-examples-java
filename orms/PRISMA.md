# Conceito de ORMs e uso do Prisma ORM em projetos Node.js + TypeScript

**Curso:** Sistemas de Informação  
**Instituição:** Instituto Federal do Norte de Minas Gerais  
**Período:** 5º período  
**Disciplina:** Programação Orientada a Objetos II

## 1. Introdução

Em aplicações modernas, é comum que a lógica do sistema seja escrita em orientação a objetos, enquanto os dados ficam armazenados em bancos de dados relacionais. Isso cria um desafio: objetos e tabelas não têm a mesma estrutura nem a mesma forma de relacionamento.

Para reduzir essa dificuldade, utilizamos ORMs (*Object-Relational Mappers*), que fazem a ponte entre o paradigma orientado a objetos e o modelo relacional. Em vez de escrever SQL o tempo todo, o programador manipula objetos e classes, enquanto o ORM traduz essas operações para consultas e comandos no banco de dados.

## 2. O que é um ORM

Um ORM é uma técnica e também um conjunto de ferramentas que permite converter dados entre sistemas orientados a objetos e bancos relacionais. Na prática, ele ajuda a representar tabelas como classes, registros como objetos e relacionamentos como associações entre entidades.

Entre os principais benefícios de um ORM, destacam-se:

- Redução de código SQL manual.
- Melhor integração com a orientação a objetos.
- Maior produtividade no desenvolvimento.
- Uso de tipagem e validação em tempo de compilação, quando a linguagem oferece suporte.
- Facilidade para manter e evoluir o sistema.

Por outro lado, o uso de ORM exige atenção a desempenho, consultas complexas e entendimento da estrutura relacional subjacente.

## 3. Prisma ORM

O Prisma ORM é um ORM moderno, aberto e muito usado com Node.js e TypeScript. Ele oferece acesso tipado ao banco de dados, sistema de migrações e uma interface visual para inspeção e edição de dados. O Prisma Client pode ser usado em aplicações backend com Node.js e TypeScript, inclusive APIs REST, GraphQL e microsserviços [web:1][web:5][web:8].

O Prisma se destaca por três componentes principais:

- **Prisma Client:** gera uma API tipada para consultas ao banco.
- **Prisma Migrate:** gerencia migrações do esquema do banco.
- **Prisma Studio:** fornece uma interface gráfica para visualizar e editar registros.

Em projetos Node.js + TypeScript, o Prisma ajuda a alinhar a modelagem do banco com os modelos do sistema, tornando o código mais legível e seguro.

## 4. Modelo conceitual e entidades

Na modelagem orientada a objetos e na modelagem de banco de dados, uma **entidade** representa algo importante do domínio do problema, como `Aluno`, `Professor`, `Disciplina` ou `Matricula`.

Uma entidade normalmente possui:

- Nome.
- Identificador único.
- Atributos descritivos.
- Relacionamentos com outras entidades.

Exemplo de entidades para um sistema acadêmico:

- `Aluno`
- `Curso`
- `Disciplina`
- `Matricula`

## 5. Relacionamentos entre entidades

O Prisma representa relacionamentos no schema com campos de relação e, quando necessário, com chaves estrangeiras. No nível conceitual, os relacionamentos mais comuns são 1:1, 1:N e N:N [page:1].

### 5.1 Um para um

Um relacionamento 1:1 ocorre quando um registro de uma entidade se relaciona com exatamente um registro de outra entidade.

Exemplo:

- Um `Aluno` possui um único `Perfil`.
- Um `Perfil` pertence a um único `Aluno`.

### 5.2 Um para muitos

Um relacionamento 1:N ocorre quando um registro de uma entidade pode estar ligado a vários registros de outra.

Exemplo:

- Um `Curso` possui vários `Alunos`.
- Um `Professor` pode ministrar várias `Disciplinas`.

### 5.3 Muitos para muitos

Um relacionamento N:N ocorre quando vários registros de uma entidade se relacionam com vários registros de outra.

Exemplo:

- Um `Aluno` pode se matricular em várias `Disciplinas`.
- Uma `Disciplina` pode ter vários `Alunos`.

Em bancos relacionais, esse tipo de relação geralmente é implementado com uma tabela intermediária.

## 6. Representação no Prisma

No Prisma, o arquivo `schema.prisma` descreve a fonte de dados, o gerador e o modelo de dados. O schema é a principal configuração do Prisma ORM [page:2].

Exemplo de modelos com relacionamentos:

```prisma
datasource db {
  provider = "postgresql"
  url      = env("DATABASE_URL")
}

generator client {
  provider = "prisma-client-js"
}

model Aluno {
  id        Int         @id @default(autoincrement())
  nome      String
  email     String      @unique
  matriculas Matricula[]
  perfil    Perfil?
}

model Perfil {
  id       Int    @id @default(autoincrement())
  bio      String?
  aluno    Aluno  @relation(fields: [alunoId], references: [id])
  alunoId  Int    @unique
}

model Curso {
  id        Int         @id @default(autoincrement())
  nome      String
  disciplinas Disciplina[]
  alunos    AlunoCurso[]
}

model Disciplina {
  id        Int         @id @default(autoincrement())
  nome      String
  matriculas Matricula[]
}

model Matricula {
  id           Int        @id @default(autoincrement())
  aluno        Aluno      @relation(fields: [alunoId], references: [id])
  alunoId      Int
  disciplina   Disciplina @relation(fields: [disciplinaId], references: [id])
  disciplinaId Int
  data         DateTime   @default(now())
}

model AlunoCurso {
  aluno    Aluno @relation(fields: [alunoId], references: [id])
  alunoId  Int
  curso    Curso @relation(fields: [cursoId], references: [id])
  cursoId  Int

  @@id([alunoId, cursoId])
}
```

### 6.1 Leitura do exemplo

- `Aluno` e `Perfil` formam uma relação 1:1.
- `Aluno` e `Matricula` formam uma relação 1:N.
- `Disciplina` e `Matricula` formam outra relação 1:N.
- `Aluno` e `Curso` são ligados por uma entidade associativa `AlunoCurso`, que representa um relacionamento N:N com atributos próprios ou necessidade de controle explícito.

No Prisma, campos como `alunoId` e `disciplinaId` são os campos escalares de relação, ou seja, as chaves estrangeiras que existem no banco. Já os campos `aluno`, `disciplina`, `matriculas` e semelhantes são campos de relação no nível do ORM [page:1].

## 7. Exemplo de uso com Prisma Client

Depois de definir o schema, o Prisma gera um cliente tipado para consultas.

```ts
import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

async function criarAluno() {
  const aluno = await prisma.aluno.create({
    data: {
      nome: "Maria Silva",
      email: "maria@ifnmg.edu.br",
    },
  });

  return aluno;
}
```

Também é possível criar dados relacionados em uma única operação:

```ts
async function criarAlunoComPerfil() {
  const aluno = await prisma.aluno.create({
    data: {
      nome: "João Souza",
      email: "joao@ifnmg.edu.br",
      perfil: {
        create: {
          bio: "Estudante de Sistemas de Informação",
        },
      },
    },
    include: {
      perfil: true,
    },
  });

  return aluno;
}
```

Esse tipo de abordagem é útil porque permite manipular objetos de forma próxima ao pensamento orientado a objetos, sem abandonar a estrutura relacional do banco.

## 8. Prisma e programação orientada a objetos

Na disciplina de Programação Orientada a Objetos, o Prisma é interessante porque aproxima o conceito de classe ao de entidade persistida. Apesar de Prisma não ser um framework de POO no sentido estrito, ele facilita a representação de objetos persistidos no banco e o uso de relações entre eles.

Pontos de conexão com POO:

- Classes e entidades têm propriedades semelhantes.
- Relacionamentos entre objetos aparecem como relacionamentos entre modelos.
- Encapsulamento é favorecido por uma camada de acesso a dados bem definida.
- A tipagem forte do TypeScript ajuda a reduzir erros em tempo de execução.

## 9. Vantagens e limitações

Principais vantagens do Prisma ORM:

- Tipagem forte com TypeScript.
- Modelagem clara das entidades.
- Suporte a migrações.
- Consultas mais seguras e organizadas.
- Boa experiência para desenvolvimento em Node.js.

Principais limitações:

- Consultas muito específicas podem exigir SQL manual.
- O desempenho depende do desenho das consultas.
- É necessário entender bem o banco relacional para evitar modelagens inadequadas.

## 10. Conclusão

ORMs são ferramentas fundamentais para integrar programação orientada a objetos e bancos de dados relacionais. O Prisma ORM se destaca por oferecer um modelo moderno, tipado e produtivo para projetos Node.js + TypeScript.

Ao aprender Prisma, o estudante desenvolve uma visão prática sobre entidades, atributos, chaves primárias, chaves estrangeiras e cardinalidades, consolidando conceitos centrais da modelagem de software e de banco de dados.

## Referências complementares

- GAMMA, Erich; HELM, Richard; JOHNSON, Ralph; VLISSIDES, John. **Design Patterns: Elements of Reusable Object-Oriented Software**. Boston: Addison-Wesley, 1994.
- FOWLER, Martin. **Patterns of Enterprise Application Architecture**. Boston: Addison-Wesley, 2002.
- SILBERSCHATZ, Abraham; KORTH, Henry F.; SUDARSHAN, S. **Database System Concepts**. 7. ed. New York: McGraw-Hill, 2019.
- PRISMA. **Prisma ORM Documentation**. Disponível em: https://www.prisma.io/docs/orm. Acesso em: 18 maio 2026.
