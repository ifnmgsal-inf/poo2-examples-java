# Testes automatizados em Java com Mockito

## Visão geral

Mockito é um framework de mocking para Java usado para simular dependências em testes unitários, permitindo que a classe sob teste seja analisada de forma isolada, sem depender da execução real de banco de dados, APIs, repositórios ou outros colaboradores externos. Esse uso é especialmente importante quando o objetivo é validar comportamento, regras de negócio e interações entre objetos.

## O que o Mockito resolve

Em muitos sistemas orientados a objetos, uma classe depende de outras para funcionar. Um serviço pode depender de um repositório, um controlador pode depender de um serviço, e uma classe de domínio aplicada a regras pode consultar validadores, gateways ou APIs externas. Quando essas dependências são usadas diretamente no teste, o resultado tende a ficar mais lento, mais frágil e mais difícil de controlar.

Mockito resolve esse problema ao permitir a criação de objetos simulados, chamados de mocks, que substituem dependências reais durante a execução do teste. Com isso, torna-se possível definir previamente o comportamento dessas dependências, verificar se certos métodos foram chamados e reproduzir cenários positivos e negativos com precisão.

## Conceitos fundamentais

### Mock

Mock é um objeto simulado que representa uma dependência da classe sob teste. Em vez de executar a implementação real, ele responde de acordo com aquilo que foi configurado no teste.

Exemplo conceitual: em vez de usar um `AlunoRepository` real conectado ao banco, o teste cria um mock de `AlunoRepository` e define qual valor ele deve retornar quando o método `salvar()` ou `buscarPorMatricula()` for chamado.

### Stub

Stub é a configuração de uma resposta específica para um mock. Na prática, isso aparece em instruções como `when(...).thenReturn(...)`, que determinam o que a dependência simulada deve devolver em um determinado cenário.

### Verificação de interação

Além de controlar respostas, Mockito também permite verificar se houve interação entre a classe testada e a dependência simulada. Isso é feito com `verify(...)`, recurso útil quando o comportamento correto depende não só do retorno final, mas também do fato de certo método ter sido invocado corretamente.

## Integração com JUnit 5

Mockito é amplamente usado junto com JUnit 5, que é o framework de testes moderno da plataforma Java. No fluxo mais comum, JUnit organiza a execução dos testes, enquanto Mockito cuida da criação e configuração dos objetos simulados.

Uma forma recomendada de integrar os dois é usar a anotação `@ExtendWith(MockitoExtension.class)`, que inicializa automaticamente os mocks declarados no teste. Isso evita inicializações manuais e deixa a classe de teste mais limpa e mais legível.

## Anotações mais importantes

### `@ExtendWith(MockitoExtension.class)`

Essa anotação integra o Mockito ao ciclo de vida do JUnit 5. Ao usá-la, os mocks e capturadores declarados são inicializados automaticamente antes de cada teste.

Exemplo:

```java
@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {
}
```

### `@Mock`

A anotação `@Mock` cria automaticamente um objeto simulado para a dependência desejada. Ela é apropriada para componentes externos à lógica principal da classe, como repositórios, clientes HTTP, gateways e serviços auxiliares.

Exemplo:

```java
@Mock
private AlunoRepository repository;
```

Nesse caso, `repository` não será um objeto real, mas um mock controlado pelo teste.

### `@InjectMocks`

A anotação `@InjectMocks` cria a instância da classe sob teste e tenta injetar nela os mocks disponíveis. Esse recurso reduz código repetitivo e ajuda a manter o foco na leitura do teste.

Exemplo:

```java
@InjectMocks
private AlunoService service;
```

Nesse cenário, a instância de `AlunoService` será criada com o mock de `AlunoRepository` injetado em sua dependência, desde que o construtor, campo ou setter permita isso.

### `@Captor`

A anotação `@Captor` cria um `ArgumentCaptor`, recurso usado para capturar o valor real enviado a um mock durante a execução do teste. Ela é útil quando o teste precisa verificar se a classe sob análise alterou corretamente um objeto antes de enviá-lo para a dependência.

Exemplo:

```java
@Captor
private ArgumentCaptor<Aluno> captor;
```

## Estrutura típica de um teste com Mockito

A lógica de um teste com Mockito costuma seguir quatro passos:

1. Preparar o cenário, criando ou injetando mocks.
2. Configurar o comportamento esperado das dependências simuladas.
3. Executar o método da classe real que está sendo testada.
4. Validar o resultado e, quando necessário, verificar interações.

Essa sequência ajuda o aluno a pensar o teste como um experimento controlado: primeiro define-se o ambiente, depois observa-se o comportamento da unidade sob análise.

## Exemplo prático completo

Considere uma classe de serviço que cadastra alunos e depende de um repositório.

### Classe de serviço

```java
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public Aluno cadastrar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório");
        }
        return repository.salvar(aluno);
    }
}
```

### Classe de teste com Mockito

```java
@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository repository;

    @InjectMocks
    private AlunoService service;

    @Captor
    private ArgumentCaptor<Aluno> captor;

    @Test
    void deveCadastrarAlunoComSucesso() {
        Aluno aluno = new Aluno("Ana");

        when(repository.salvar(aluno)).thenReturn(aluno);

        Aluno salvo = service.cadastrar(aluno);

        assertEquals("Ana", salvo.getNome());
        verify(repository).salvar(aluno);
    }
}
```

Nesse exemplo, o teste não acessa banco de dados, não depende de configuração externa e não usa uma implementação real de repositório. O objetivo é verificar se a regra do serviço funciona corretamente e se ele delega a persistência ao colaborador esperado.

## Entendendo o fluxo do exemplo

O trecho `when(repository.salvar(aluno)).thenReturn(aluno)` configura o comportamento do mock. Isso significa que, quando a classe sob teste chamar `salvar(aluno)`, o mock devolverá exatamente o objeto informado.

Em seguida, `service.cadastrar(aluno)` executa o método real da classe de serviço. É nesse ponto que a lógica de negócio é realmente exercitada.

Depois disso, o teste usa `assertEquals(...)` para validar o resultado e `verify(repository).salvar(aluno)` para confirmar que a dependência foi acionada corretamente. A combinação entre asserção de resultado e verificação de interação costuma produzir testes mais expressivos quando existe colaboração entre objetos [cite:11][cite:13].

## Simulando exceções

Mockito também permite simular falhas, o que é importante para testar robustez e tratamento de erro. Isso pode ser feito com `thenThrow(...)`.

Exemplo:

```java
@Test
void deveLancarExcecaoQuandoRepositorioFalhar() {
    Aluno aluno = new Aluno("Ana");

    when(repository.salvar(aluno))
        .thenThrow(new RuntimeException("Erro ao salvar"));

    assertThrows(RuntimeException.class, () -> service.cadastrar(aluno));
}
```

Esse tipo de cenário ajuda a mostrar aos alunos que um bom conjunto de testes não cobre apenas o caminho feliz, mas também casos de falha e comportamento inesperado [cite:18][cite:21].

## Capturando argumentos com `@Captor`

Em alguns cenários, não basta verificar se o método foi chamado. É necessário verificar qual objeto foi efetivamente enviado ao mock [cite:23]. Nessa situação, o `ArgumentCaptor` se torna uma ferramenta muito útil [cite:23].

Exemplo:

```java
@Test
void deveEnviarAlunoCorretoParaPersistencia() {
    Aluno aluno = new Aluno("Ana");

    when(repository.salvar(aluno)).thenReturn(aluno);

    service.cadastrar(aluno);

    verify(repository).salvar(captor.capture());
    Aluno enviado = captor.getValue();

    assertEquals("Ana", enviado.getNome());
}
```

Esse padrão é especialmente importante quando a classe modifica o objeto antes de persisti-lo, por exemplo definindo data de cadastro, status inicial ou identificadores internos [cite:23].

## Boas práticas no uso do Mockito

Algumas práticas tornam os testes com Mockito mais úteis e mais fáceis de manter [cite:11][cite:18]:

- Testar comportamento, e não detalhes internos da implementação [cite:11][cite:18].
- Usar mocks apenas nas dependências necessárias [cite:11][cite:13].
- Escrever nomes de testes descritivos, informando cenário, ação e resultado esperado [cite:18][cite:21].
- Cobrir tanto casos de sucesso quanto casos de falha [cite:18][cite:21].
- Evitar verificações excessivas com `verify(...)` quando o retorno já demonstra claramente o comportamento esperado [cite:11][cite:13].

Essas recomendações ajudam a evitar testes frágeis, excessivamente acoplados ao código interno e difíceis de refatorar [cite:11][cite:18].

## Erros comuns dos estudantes

Os erros mais frequentes em testes com Mockito costumam estar ligados mais à estratégia do teste do que à sintaxe da ferramenta [cite:11][cite:13]. Entre os principais problemas estão o excesso de mocks, a verificação desnecessária de toda interação e a ausência de cenários negativos [cite:11][cite:13].

Outro erro comum é transformar o teste em uma cópia da implementação, repetindo exatamente a lógica interna da classe em vez de validar o seu comportamento observável [cite:11][cite:18]. Quando isso acontece, o teste perde valor pedagógico e prático, pois deixa de funcionar como proteção contra regressões reais [cite:11][cite:18].

## Sugestão de abordagem didática

Para trabalhar esse conteúdo em aula, uma boa sequência é começar com uma classe simples de serviço, apresentar a dependência real e perguntar por que seu uso direto dificulta o teste [cite:11][cite:17]. Em seguida, pode-se substituir essa dependência por um mock, configurando um retorno com `when(...).thenReturn(...)` e demonstrando a diferença entre testar o comportamento da regra e testar a infraestrutura [cite:11][cite:23].

Depois disso, convém mostrar explicitamente o papel das anotações `@Mock`, `@InjectMocks`, `@ExtendWith(MockitoExtension.class)` e `@Captor`, pois elas tornam o teste mais próximo da prática profissional e mais limpo para leitura em sala [cite:18][cite:23]. Fechar com um caso de exceção e uma verificação usando `verify(...)` ajuda a consolidar a ideia de cenário, ação e validação [cite:13][cite:23].

## Referências bibliográficas

- Acharya, N. *Mockito Essentials*. Packt Publishing, 2014 [cite:16].
- Grzejszczak, A. *Mockito Cookbook*. Packt Publishing, 2014 [cite:22].
- Szczepanek, M. et al. *Mockito Made Clear*. O'Reilly Media, 2023 [cite:13].
- JUnit Team. *JUnit User Guide* [cite:21].
- Mockito Contributors. *Mockito framework site* [cite:11].
