package org.example.generics;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static Character[] charArray = {'d', 's', 'p', 'w', 'b'};
    static Integer[] intArray = { 1, 2, 3, 4, 5};
    static Boolean[] boolArray = {true, false, true};

    public static void main(String[] args) {
        //System.out.println("Generics Examples!");
        //listaSemTipagem();
        //listaComTipagem();
        // MAS SE QUISERMOS UMA LISTA ADAPTÁVEL A DIFERENTES TIPOS?
        //listaComObject();
        // FINALMENTE! USANDO GENERICS !!!
        //listaComGenerics();
        
        mutableStackExample();
    }

    private static void mutableStackExample() {
        MutableStack<Float> stack = new MutableStack<Float>(0.62f, 3.14f, 2.7f);
        stack.push(9.87f);
        System.out.println(stack);

        System.out.println("peek(): " + stack.peek());
        System.out.println(stack);

        for (int i=stack.size(); i> 0; i--) {
            System.out.println("pop(): " + stack.pop());
            System.out.println(stack);
        }
    }

    private static void listaSemTipagem() {
        List names = new ArrayList();
        names.add("Kelly");
        String name = (String) names.get(0);
        System.out.println("Nome: " + name);
        names.add(8);  // Sem erro de tipo!
    }

    private static void listaComTipagem() {
        List<String> names = new ArrayList<>();  // Agora apenas para String
        names.add("Kelly");
        String name = names.get(0);
        System.out.println("Nome: " + name);
        //names.add(8);  // Erro de tipo!
    }

    public static List arrayToList(Object[] array, List<Object> list) {
        for (Object obj: array) {
            list.add(obj);
        }
        return list;
    }

    private static void listaComObject() {
        List<Character> charList = arrayToList(charArray, new ArrayList<>());
        List<Boolean> boolList = arrayToList(boolArray, new ArrayList<>());
        List<Integer> intList = arrayToList(intArray, new ArrayList<>());
        System.out.println("Primeiro item char: " + charList.get(0));
        System.out.println("Primeiro item bool: " + boolList.get(0));
        System.out.println("Primeiro item int: " + intList.get(0));
//        // ENTRETANTO, SE TROCARMOS O TIPO POR ALGUM ENGANO
//        List<String> intList2 = arrayToList(intArray, new ArrayList<>());
//        // NAO TEVE ERRO DE COMPILACAO. POREM, AO ACESSAR UM OBJETO:
//        System.out.println("Primeiro item int: " + intList2.get(0));
//        // NAO EVITA ClassCastException
    }

    public static <T> List<T> arrayToListGenerics(T[] array, List<T> list) {
        for (T obj: array) {
            list.add(obj);
        }
        return list;
    }

    private static void listaComGenerics() {
        List<Character> charList = arrayToListGenerics(charArray, new ArrayList<>());
        List<Boolean> boolList = arrayToListGenerics(boolArray, new ArrayList<>());
        List<Integer> intList = arrayToListGenerics(intArray, new ArrayList<>());
        System.out.println("Primeiro item char com generics: " + charList.get(0));
        System.out.println("Primeiro item bool com generics: " + boolList.get(0));
        System.out.println("Primeiro item int com generics: " + intList.get(0));
//        // ENTRETANTO, SE TROCARMOS O TIPO POR ALGUM ENGANO
//        List<String> intList2 = arrayToListGenerics(intArray, new ArrayList<>());
//        // NAO TEVE ERRO DE COMPILACAO. POREM, AO ACESSAR UM OBJETO:
//        System.out.println("Primeiro item int: " + intList2.get(0));
//        // NAO EVITA ClassCastException
    }
}