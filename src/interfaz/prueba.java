package interfaz;

import clases.Pokemon;
import clases.Equipo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prueba extends JFrame {

    public static void main(String[] args) {
        Pokemon pokemonClase;
        JFrame frame = new JFrame("PC Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("PC de Pokémon", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(10, 1, 10, 10));
        frame.add(mainMenuPanel, BorderLayout.CENTER);

        JButton equipoButton = new JButton("Equipo");
        JButton cajasButton = new JButton("Cajas");
        JButton sacarPokemonButton = new JButton("Sacar Pokémon");
        JButton dejarPokemonButton = new JButton("Dejar Pokémon");
        JButton moverPokemonButton = new JButton("Mover Pokémon");
        JButton moverObjetosButton = new JButton("Mover Objetos");
        JButton desconectarButton = new JButton("Desconectar");

        equipoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        cajasButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sacarPokemonButton.setFont(new Font("Arial", Font.PLAIN, 16));
        dejarPokemonButton.setFont(new Font("Arial", Font.PLAIN, 16));
        moverPokemonButton.setFont(new Font("Arial", Font.PLAIN, 16));
        moverObjetosButton.setFont(new Font("Arial", Font.PLAIN, 16));
        desconectarButton.setFont(new Font("Arial", Font.PLAIN, 16));

        mainMenuPanel.add(equipoButton);
        mainMenuPanel.add(cajasButton);
        mainMenuPanel.add(sacarPokemonButton);
        mainMenuPanel.add(dejarPokemonButton);
        mainMenuPanel.add(moverPokemonButton);
        mainMenuPanel.add(moverObjetosButton);
        mainMenuPanel.add(desconectarButton);

        equipoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarEquipo(frame);
            }
        });

        cajasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCajas(frame);
            }
        });

        sacarPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacarPokemon(frame);
            }
        });

        dejarPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dejarPokemon(frame);
            }
        });

        moverPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverPokemon(frame);
            }
        });

        moverObjetosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverObjetos(frame);
            }
        });

        desconectarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desconectar(frame);
            }
        });

        frame.setVisible(true);
    }

    private static void mostrarEquipo(JFrame parentFrame) {
        JDialog equipoDialog = new JDialog(parentFrame, "Equipo", true);
        equipoDialog.setSize(500, 400);
        equipoDialog.setLayout(new BorderLayout());

        JPanel equipoPanel = new JPanel();
        equipoPanel.setLayout(new GridLayout(6, 1, 10, 10));
        equipoDialog.add(equipoPanel, BorderLayout.CENTER);

        String[] equipo = obtenerEquipoDesdeBD();

        for (int i = 0; i < 6; i++) {
            String pokemonNombre = i < equipo.length ? equipo[i] : "Empty Slot " + (i + 1);
            JButton pokemonButton = new JButton(pokemonNombre);
            pokemonButton.setFont(new Font("Arial", Font.PLAIN, 16));
            equipoPanel.add(pokemonButton);

            pokemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!pokemonNombre.startsWith("Empty")) {
                        mostrarOpcionesPokemon(equipoDialog, pokemonNombre);
                    } else {
                        JOptionPane.showMessageDialog(equipoDialog, "No hay Pokémon en este slot.");
                    }
                }
            });
        }

        equipoDialog.setVisible(true);
    }

    private static void mostrarOpcionesPokemon(JDialog parentDialog, String pokemonNombre) {
        JDialog opcionesDialog = new JDialog(parentDialog, pokemonNombre, true);
        opcionesDialog.setSize(400, 300);
        opcionesDialog.setLayout(new GridLayout(4, 1, 10, 10));

        JButton datosButton = new JButton("Datos");
        JButton objetoButton = new JButton("Objeto");
        JButton moverButton = new JButton("Mover");
        JButton atrasButton = new JButton("Atrás");

        datosButton.setFont(new Font("Arial", Font.PLAIN, 16));
        objetoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        moverButton.setFont(new Font("Arial", Font.PLAIN, 16));
        atrasButton.setFont(new Font("Arial", Font.PLAIN, 16));

        opcionesDialog.add(datosButton);
        opcionesDialog.add(objetoButton);
        opcionesDialog.add(moverButton);
        opcionesDialog.add(atrasButton);

        // Añadir ActionListeners a los botones
        datosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatos(opcionesDialog, pokemonNombre);
            }
        });

        objetoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarObjeto(opcionesDialog, pokemonNombre);
            }
        });

        moverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverPokemonEquipo(opcionesDialog, pokemonNombre);
            }
        });

        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opcionesDialog.dispose();
            }
        });

        opcionesDialog.setVisible(true);
    }

    private static void mostrarDatos(JDialog parentDialog, String pokemonNombre) {
        Pokemon pokemon = obtenerDatosPokemonDesdeBD(pokemonNombre);
        JOptionPane.showMessageDialog(parentDialog,
                "Nombre: " + pokemonNombre + "\nHP: " + pokemon.HP + "\nAtaque: " + pokemon.ataque +
                        "\nDefensa: " + pokemon.defensa + "\nNivel: " + pokemon.nivel);
    }

    private static void mostrarObjeto(JDialog parentDialog, String pokemonNombre) {
        String objeto = obtenerObjetoDesdeBD(pokemonNombre);
        if (objeto == null || objeto.isEmpty()) {
            JOptionPane.showMessageDialog(parentDialog, pokemonNombre + " no porta ningún objeto.");
        } else {
            JOptionPane.showMessageDialog(parentDialog, pokemonNombre + " porta el objeto: " + objeto);
        }
    }

    private static void moverPokemonEquipo(JDialog parentDialog, String pokemonNombre) {
        JOptionPane.showMessageDialog(parentDialog, "Funcionalidad para mover " + pokemonNombre + " en el equipo.");
    }

    private static void mostrarCajas(JFrame parentFrame) {
        JDialog cajasDialog = new JDialog(parentFrame, "Cajas", true);
        cajasDialog.setSize(500, 400);
        cajasDialog.setLayout(new GridLayout(5, 10, 5, 5));

        String[] pokemonsEnCajas = obtenerPokemonsDesdeBD();

        for (String pokemonNombre : pokemonsEnCajas) {
            JButton pokemonButton = new JButton(pokemonNombre);
            pokemonButton.setFont(new Font("Arial", Font.PLAIN, 12));
            cajasDialog.add(pokemonButton);

            pokemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarOpcionesPC(cajasDialog, pokemonNombre);
                }
            });
        }

        cajasDialog.setVisible(true);
    }

    private static void mostrarOpcionesPC(JDialog parentDialog, String pokemonNombre) {
        JDialog opcionesDialog = new JDialog(parentDialog, pokemonNombre, true);
        opcionesDialog.setSize(400, 300);
        opcionesDialog.setLayout(new GridLayout(5, 1, 10, 10));

        JButton sacarButton = new JButton("Sacar");
        JButton datosButton = new JButton("Datos");
        JButton cambiarApodoButton = new JButton("Cambiar Apodo");
        JButton liberarButton = new JButton("Liberar");
        JButton salirButton = new JButton("Salir");

        sacarButton.setFont(new Font("Arial", Font.PLAIN, 16));
        datosButton.setFont(new Font("Arial", Font.PLAIN, 16));
        cambiarApodoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        liberarButton.setFont(new Font("Arial", Font.PLAIN, 16));
        salirButton.setFont(new Font("Arial", Font.PLAIN, 16));

        opcionesDialog.add(sacarButton);
        opcionesDialog.add(datosButton);
        opcionesDialog.add(cambiarApodoButton);
        opcionesDialog.add(liberarButton);
        opcionesDialog.add(salirButton);

        sacarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Pokemon< 6) {

                    sacarPokemonDeBD(pokemonNombre);
                    opcionesDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(opcionesDialog, "El equipo está lleno.");
                }
            }
        });

        datosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatos(opcionesDialog, pokemonNombre);
            }
        });

        cambiarApodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog(opcionesDialog, "Introduce el nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {




                    cambiarApodoEnBD(pokemonNombre, nuevoNombre);
                    opcionesDialog.dispose();
                }
            }
        });

        liberarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(opcionesDialog, "¿Estás seguro de liberar " + pokemonNombre + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {




                    liberarPokemonDeBD(pokemonNombre);
                    opcionesDialog.dispose();
                }
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opcionesDialog.dispose();
            }
        });

        opcionesDialog.setVisible(true);
    }

    private static void sacarPokemon(JFrame parentFrame) {
        mostrarCajas(parentFrame);
    }

    private static void dejarPokemon(JFrame parentFrame) {
        JDialog dejarDialog = new JDialog(parentFrame, "Dejar Pokémon", true);
        dejarDialog.setSize(400, 300);
        dejarDialog.setLayout(new GridLayout(6, 1, 10, 10));




        String[] equipo = obtenerEquipoDesdeBD();

        for (String pokemonNombre : equipo) {
            JButton pokemonButton = new JButton(pokemonNombre);
            pokemonButton.setFont(new Font("Arial", Font.PLAIN, 16));
            dejarDialog.add(pokemonButton);

            pokemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarOpcionesDejar(dejarDialog, pokemonNombre);
                }
            });
        }

        dejarDialog.setVisible(true);
    }

    private static void mostrarOpcionesDejar(JDialog parentDialog, String pokemonNombre) {
        JDialog opcionesDialog = new JDialog(parentDialog, pokemonNombre, true);
        opcionesDialog.setSize(400, 300);
        opcionesDialog.setLayout(new GridLayout(5, 1, 10, 10));

        JButton dejarButton = new JButton("Dejar");
        JButton datosButton = new JButton("Datos");
        JButton cambiarApodoButton = new JButton("Cambiar Apodo");
        JButton liberarButton = new JButton("Liberar");
        JButton salirButton = new JButton("Salir");

        dejarButton.setFont(new Font("Arial", Font.PLAIN, 16));
        datosButton.setFont(new Font("Arial", Font.PLAIN, 16));
        cambiarApodoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        liberarButton.setFont(new Font("Arial", Font.PLAIN, 16));
        salirButton.setFont(new Font("Arial", Font.PLAIN, 16));

        opcionesDialog.add(dejarButton);
        opcionesDialog.add(datosButton);
        opcionesDialog.add(cambiarApodoButton);
        opcionesDialog.add(liberarButton);
        opcionesDialog.add(salirButton);

        dejarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                dejarPokemonEnBD(pokemonNombre);
                opcionesDialog.dispose();
            }
        });

        datosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatos(opcionesDialog, pokemonNombre);
            }
        });

        cambiarApodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog(opcionesDialog, "Introduce el nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {



                    cambiarApodoEnBD(pokemonNombre, nuevoNombre);
                    opcionesDialog.dispose();
                }
            }
        });

        liberarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(opcionesDialog, "¿Estás seguro de liberar " + pokemonNombre + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {



                    liberarPokemonDeBD(pokemonNombre);
                    opcionesDialog.dispose();
                }
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opcionesDialog.dispose();
            }
        });

        opcionesDialog.setVisible(true);
    }

    private static void moverPokemon(JFrame parentFrame) {
        JDialog moverDialog = new JDialog(parentFrame, "Mover Pokémon", true);
        moverDialog.setSize(600, 400);
        moverDialog.setLayout(new GridLayout(2, 1, 10, 10));

        JButton equipoButton = new JButton("Mover desde equipo");
        JButton pcButton = new JButton("Mover desde PC");

        equipoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        pcButton.setFont(new Font("Arial", Font.PLAIN, 16));

        moverDialog.add(equipoButton);
        moverDialog.add(pcButton);

        equipoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dejarPokemon(parentFrame);
            }
        });

        pcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacarPokemon(parentFrame);
            }
        });

        moverDialog.setVisible(true);
    }

    private static void moverObjetos(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Funcionalidad para mover objetos entre Pokémon.");
    }

    private static void desconectar(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Desconectando y guardando cambios...");
        System.exit(0);
    }

    private static String[] obtenerEquipoDesdeBD() {
        return new String[]{"Pikachu", "Bulbasaur", "Charmander"};
    }

    private static String[] obtenerPokemonsDesdeBD() {
        return new String[]{"Squirtle", "Caterpie", "Pidgey"};
    }

    private static Pokemon obtenerDatosPokemonDesdeBD(String pokemonNombre) {
        return new Pokemon(pokemonNombre, 50, 50, 50, 5);
    }

    private static String obtenerObjetoDesdeBD(String pokemonNombre) {
        return "Poción";
    }

    private static void sacarPokemonDeBD(String pokemonNombre) {



    }

    private static void dejarPokemonEnBD(String pokemonNombre) {



    }

    private static void cambiarApodoEnBD(String pokemonNombre, String nuevoNombre) {



    }

    private static void liberarPokemonDeBD(String pokemonNombre) {



    }
}