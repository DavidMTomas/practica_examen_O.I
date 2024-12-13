package com.damato.ejercicio1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App1 {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        boolean salir = false;
        File archivo = new File("./src/main/resources/usuarios.dat");
        try {
            while (!salir) {
                menu();
                String opcion = in.nextLine();
                switch (opcion) {
                    case "1":
                        agregar(in, archivo);
                        break;
                    case "2":
                        listar(archivo);
                        break;
                    case "3":
                        eliminar(in, archivo);
                        break;
                    case "4":
                        buscar(in, archivo);
                        break;
                    case "5":
                        editar(in, archivo);
                        break;
                    case "6":
                        salir = true;
                        System.out.println("Programa finalizado");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                        break;
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Cerramos el stream de teclado
        in.close();
    } // Fin del main

    private static void editar(Scanner in, File archivo) throws IOException, ClassNotFoundException {
        ArrayList<Usuario> lista = listar(archivo);
        System.out.println("Que id desea Editar?");
        int id = in.nextInt();
        in.nextLine();

        for (Usuario u : lista) {
            if (u.getId() == id) {
                System.out.println("Nueva id");
                u.setId(in.nextInt());
                in.nextLine();
                System.out.println("Nueva nombre");
                u.setNombre(in.nextLine());
                System.out.println("Nuevo email");
                u.setEmail(in.nextLine());
            }
        }
        reescribirArchivo(lista, archivo);
    }

    private static void buscar(Scanner in, File archivo) throws IOException, ClassNotFoundException {
        ArrayList<Usuario> lista = listar(archivo);
        System.out.println("Que id desea buscar?");
        int id = in.nextInt();
        in.nextLine();
        lista.stream().filter(it -> it.getId() == id).forEach(System.out::println);
    }

    private static void eliminar(Scanner in, File archivo) throws IOException, ClassNotFoundException {
        ArrayList<Usuario> lista = listar(archivo);
        System.out.println("Que id desea eliminar?");

        //busca usuario
        int id = in.nextInt();
        in.nextLine();
        for (Usuario usuario : lista) {
            if (usuario.getId() == id) {
                lista.remove(usuario);
                break;
            }
        }
        System.out.println("El usuario eliminado es: " + id);

        reescribirArchivo(lista, archivo);


    }

    private static void reescribirArchivo(ArrayList<Usuario> lista, File archivo) throws IOException {
        // reeescribimos archivo
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
        for (Usuario usuario : lista) {
            oos.writeObject(usuario);
        }
        oos.close();
        System.out.println("Lista actualizada");
    }

    private static ArrayList listar(File archivo) throws IOException, ClassNotFoundException {
        ArrayList<Usuario> lista = new ArrayList<>();
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(archivo));

        try {
            while (true) {
                Usuario user = (Usuario) input.readObject();
                lista.add(user);
            }
        } catch (EOFException e) {
            System.out.println("Fin iteracion");
        }
        input.close();
        lista.stream().forEach(System.out::println);

        return lista;

    }

    private static void agregar(Scanner in, File archivo) throws IOException {
        System.out.println("Inserta id");
        int id;
        while (true) {
            if (!in.hasNextInt()) {
                System.out.println("Ingrese un numero para el ID");
                in.nextLine();
            } else {
                id = in.nextInt();
                in.nextLine();
                break;
            }
        }
        System.out.println("Nombre de usuario");
        String nombre = in.nextLine();
        System.out.println("email");
        String email = in.nextLine();
        Usuario usuario = new Usuario(id, nombre, email);

        if (!archivo.exists()) {
            try {
                System.out.println(archivo.createNewFile() ? "archivo creado" : "Error");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        ObjectOutputStream out;
        if (archivo.length() > 0) out = new AppendableObjectOutputStream(new FileOutputStream(archivo, true));
        else out = new ObjectOutputStream(new FileOutputStream(archivo));

        out.writeObject(usuario);

        System.out.println("Usuario agragado al archivo");

        out.close();
    }

    private static void menu() {
        String menu = """
                
                1.- Añadir
                2.- Listar
                3.- Eliminar
                4.- Buscar
                5.- Editar
                6.- Salir
                
                """;

        System.out.println(menu);
    }
}
