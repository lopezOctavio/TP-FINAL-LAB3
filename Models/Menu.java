package Models;

import Excepciones.Invalido;
import Excepciones.OpcionNoValida;
import Excepciones.UsuarioPasswordInvalido;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    //region MENU GENERAL
    public static void menuGeneral(TecBeer sistema) {
        /**IMPORTANTE*//*en la seccion de productos*/
        //HAY QUE TRAER TODOS LOS PRODUCTOS QUE SE PUEDEN GUARDAR EN UN TREEMAP
        //VER SI TENEMOS QUE HACER DEVUELTA EL BALANCEADO BINARIO PARA QUE SEA MAS RAPIDA LA BUSQUEDA


        /**IMPORTANTE*/
        //HAY QUE TRAER TODOS LOS CLIENTES DESDE ARCHIVOS CON SUS PEDIDOS Y LOS PRODUCTOS DEL PEDIDO




        int opcion = -1;
        do{
            do{
                Consola.escribir("-----BIENVENIDO-----");
                Consola.escribir("1.LOG IN");
                Consola.escribir("2.REGISTRARSE");
                Consola.escribir("0.SALIR");
                opcion = Consola.leerInt("Seleccione una opcion <!>");
                try {
                    if(opcion < 0 || opcion > 2){
                        throw new OpcionNoValida("Error. Debe ingresar una opción válida");
                    }
                }catch (Exception e){
                    Consola.escribir(e.getMessage());
                }
            }while(opcion < 0 || opcion > 2);

            switch (opcion){
                case 1:
                    //ACA HAGO EL LOG IN VALIDANDO QUE INGRESE UN USUARIO CORRECTO Y SI ESO SUCEDE AHI QUE VALIDE LA CONTRASENIA
                    logIn(sistema);
                    break;
                case 2:
                    //LA PARTE DE REGISTRARSE ES LA DE CREAR UN NUEVO USUARIO
                    registrarse(sistema);
                    break;
                case 0:
                    Consola.escribir("Saliendo del sistema <!>");
                    break;
            }
        }while(opcion!=0);
    }

    //endregion

    //region LOG IN
    public static void logIn(TecBeer sistema) {

        Consola.escribir("-----LOG IN-----");
        Persona personaAux;
        Consola.limpiar();


        //VERIFICO QUE EL USERNAME Y LA PASSWORD SEAN CORRECTAS
        try {
            String guardaUser = Consola.leerString("Ingrese usuario: ");
            boolean flagUsuario = sistema.verificarUsuario(guardaUser);
            String guardaPassword = Consola.leerString("Ingrese Password: ");
            boolean flagPassword = sistema.verificarPassword(guardaPassword);

            if(flagPassword && flagUsuario){
                personaAux = sistema.devolverPersonaPorUserName(guardaUser);

                if(personaAux.getRol() == 0){
                    //ACA MUESTRO EL MENU PARA USUARIO
                    System.out.println("-----MENU USUARIO-----");
                    subMenuUsuario(sistema, (Cliente) personaAux);

                }else{
                    //ACA MUESTRO EL MENU PARA ADMIN
                    System.out.println("-----MENU ADMIN-----");
                    subMenuAdmin(sistema);
                }
            }else {
                throw new UsuarioPasswordInvalido("Usuario o password incorrecto.");
            }
        }catch (Exception e){
            Consola.escribir(e.getMessage());
            Consola.escribir("Presione cualquier tecla para continuar");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
    }

    public static void registrarse(TecBeer sistema){

        Cliente cliente = new Cliente();
        cliente.alta();
        sistema.addToMapPersona(cliente);
        Consola.escribir("Usted se ha registrado exitosamente en TecBeer.");
        Consola.escribir("Presione cualquier tecla para continuar");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    //endregion

    //region USUARIO
    public static void subMenuUsuario(TecBeer sistema, Cliente cliente){
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Cliente");
                Consola.escribir("2.Pedido");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>2);

            switch (opcion){
                case 1:
                    usuarioCliente(sistema, cliente);
                    break;
                case 2:
                    usuarioPedido();
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);
    }
    //endregion

    //region ADMIN
    public static void subMenuAdmin(TecBeer sistema) throws IOException {
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Cliente");
                Consola.escribir("2.Pedido");
                Consola.escribir("3.Producto");
                Consola.escribir("4.Liberar sistema");
                Consola.escribir("5.Archivos");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>5);

            switch (opcion){
                case 1:
                    adminCliente(sistema);
                    break;
                case 2:
                    adminPedido();
                    break;
                case 3:
                    adminProducto(sistema);
                    break;
                case 4:
                    break;
                case 5:
                    //subMenuArchivos(sistema);
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);

    }
    //endregion

    //region USUARIO - CLIENTE
    public static void usuarioCliente(TecBeer sistema, Cliente cliente){
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Ver mi usuario");
                Consola.escribir("2.Baja");
                Consola.escribir("3.Modificar");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>5);

            switch (opcion){
                case 1:
                    Consola.escribir(sistema.devolverPersonaPorUserName(cliente.getUsername()).toString());
                    Consola.escribir("Presione cualquier tecla para continuar");
                    sc.nextLine();
                    break;
                case 2:
                    cliente.baja(sistema, cliente);
                    Consola.escribir("Presione cualquier tecla para continuar");
                    sc.nextLine();
                    break;
                case 3:
                    cliente.modificacion(sistema);
                    Consola.escribir("Se ha modificado exitosamente en el sistema.");
                    Consola.escribir("Presione cualquier tecla para continuar");
                    sc.nextLine();
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);
    }
    //endregion

    //region USUARIO - PEDIDO
    public static void usuarioPedido(){
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Ver pedido");
                Consola.escribir("2.Hacer pedido");
                Consola.escribir("3.Eliminar pedido");
                Consola.escribir("4.Ver productos");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>4);

            switch (opcion){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);
    }
    //endregion

    //region ADMIN - CLIENTE
    public static void adminCliente(TecBeer sistema){
        Scanner sc = new Scanner(System.in);
        String username;
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Ver un cliente");
                Consola.escribir("2.Ver todos los clientes");
                Consola.escribir("3.Alta cliente");
                Consola.escribir("4.Alta admin");
                Consola.escribir("5.Baja");
                Consola.escribir("6.Activar cliente");
                Consola.escribir("7.Modificar");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>7);

            switch (opcion){
                case 1:
                    Consola.escribir("Ingrese el Username a buscar: ");
                    username = sc.nextLine();
                    try{
                        if(sistema.devolverPersonaPorUserName(username) != null && sistema.devolverPersonaPorUserName(username) instanceof Cliente){
                            Consola.escribir("El Username ingresado pertenece al siguiente cliente: ");
                            Consola.escribir(sistema.devolverPersonaPorUserName(username).toString());
                            Consola.escribir("Presione cualquier tecla para continuar");
                            sc.nextLine();
                        }
                        else throw new Invalido("El Username ingresado no pertenece a un cliente válido.");
                    }catch (Exception e){
                        Consola.escribir(e.getMessage());
                    }
                    break;
                case 2:
                    sistema.verTodosLosClientes();
                    Consola.escribir("Presione cualquier tecla para continuar");
                    sc.nextLine();
                    break;
                case 3:
                    Cliente cliente = new Cliente();
                    cliente.alta();
                    sistema.addToMapPersona(cliente);
                    Consola.escribir("El nuevo cliente se ha agregado exitosamente a Tecbeer.");
                    Consola.escribir("Presione cualquier tecla para continuar");
                    sc.nextLine();
                    break;
                case 4:
                    Admin admin = new Admin();
                    admin.alta();
                    sistema.addToMapPersona(admin);
                    Consola.escribir("El nuevo admin se ha agregado exitosamente a Tecbeer.");
                    Consola.escribir("Presione cualquier tecla para continuar");
                    sc.nextLine();
                    break;
                case 5:
                    Consola.escribir("Ingrese el Username del cliente que desea dar de baja: ");
                    username = sc.nextLine();
                    try{
                        if(sistema.devolverPersonaPorUserName(username) != null && sistema.devolverPersonaPorUserName(username) instanceof Cliente){
                            sistema.removeToMapPersona(sistema.devolverPersonaPorUserName(username));
                            Consola.escribir("El cliente ingresado ha sido dado de baja de Tecbeer.");
                            Consola.escribir("Presione cualquier tecla para continuar");
                            sc.nextLine();
                        }else throw new Invalido("El Username ingresado no pertenece a un cliente válido.");
                    }catch (Exception e){
                        Consola.escribir(e.getMessage());
                    }
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);
    }
    //endregion

    //region ADMIN - PEDIDO
    public static void adminPedido(){
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Ver pedidos");
                Consola.escribir("2.Ver todos los pedidos de un cliente");
                Consola.escribir("3.Eliminar pedido");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>3);

            switch (opcion){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);
    }
    //endregion

    //region ADMIN - PRODUCTO
    public static void adminProducto(TecBeer sistema){
        sistema.arrayListToMapCerveza();
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Alta");
                Consola.escribir("2.Baja");
                Consola.escribir("3.Modificar");
                Consola.escribir("4.Eliminar");
                Consola.escribir("5.Activar producto");
                Consola.escribir("6.Ver todos los productos");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>6);

            switch (opcion){
                case 1:
                    Cerveza cerveza = new Cerveza();
                    cerveza.alta();
                    sistema.addToMapCerveza(cerveza);
                    Consola.escribir("El nuevo Producto se ha agregado exitosamente a Tecbeer.");
                    Consola.escribir("Presione cualquier tecla para continuar");
                    sc.nextLine();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    sistema.verTodosLosProductos();
                    Consola.escribir("Presione cualquier tecla para continuar");
                    sc.nextLine();
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);
    }
    //endregion

    //region PRODUCTOS POR ESTILO
    public static void verProductoPorEstiloMenu(){
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Rubia");
                Consola.escribir("2.Roja");
                Consola.escribir("3.Lupulada");
                Consola.escribir("4.Negra");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>4);

            switch (opcion){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);
    }
    //endregion

    //region ARCHIVOS
    /*
    public static void subMenuArchivos(TecBeer sistema) throws IOException {
        int opcion = -1;
        do{
            do{
                Consola.escribir("1.Archivo clientes");
                Consola.escribir("2.Archivo productos");
                Consola.escribir("3.Archivo cabecera"); //ARCHIVO CABECERA Y DETALLE NO LOS UTILIZAMOS.
                Consola.escribir("4.Archivo detalle");
                Consola.escribir("0.Salir");
                opcion=Consola.leerInt("Seleccione una opcion <!>");
            }while(opcion<0||opcion>4);

            switch (opcion){
                case 1:
                    /*ArrayList<Persona> arrayPersonas = new ArrayList<>();
                    arrayPersonas = Archivo.leerArchivoYDevolverPersonas();
                    for(Persona persona:arrayPersonas){
                        System.out.println(persona.toString());
                    }
                    Archivo.leerArchivoYDevolverPersonas();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    break;
            }
        }while(opcion!=0);
    }*/
    //endregion
}