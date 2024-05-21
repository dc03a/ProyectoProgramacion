package interfaz;

import clases.*;
import utilidadesJSON.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class miniPrueba extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("PC Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Configurar el color de fondo y el título
        frame.getContentPane().setBackground(new Color(240, 248, 255));  // Color de fondo claro
        JLabel titleLabel = new JLabel("PC de Pokémon", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Pokemon GB", Font.BOLD, 24));  // Fuente personalizada
        titleLabel.setForeground(new Color(45, 52, 54));  // Color del texto
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel principal con el menú
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(7, 1, 10, 10));
        mainMenuPanel.setBackground(new Color(240, 248, 255));
        frame.add(mainMenuPanel, BorderLayout.CENTER);

        // Botones del menú principal
        JButton equipoButton = new JButton("Equipo");
        JButton cajasButton = new JButton("Cajas");
        JButton sacarPokemonButton = new JButton("Sacar Pokémon");
        JButton dejarPokemonButton = new JButton("Dejar Pokémon");
        JButton moverPokemonButton = new JButton("Mover Pokémon");
        JButton moverObjetosButton = new JButton("Mover Objetos");
        JButton desconectarButton = new JButton("Desconectar");

        // Configuración de botones
        JButton[] buttons = {equipoButton, cajasButton, sacarPokemonButton, dejarPokemonButton, moverPokemonButton, moverObjetosButton, desconectarButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Pokemon GB", Font.PLAIN, 16));  // Fuente personalizada
            button.setBackground(new Color(223, 230, 233));  // Color de fondo del botón
            button.setForeground(new Color(45, 52, 54));  // Color del texto del botón
            mainMenuPanel.add(button);
        }

        // Añadir ActionListeners a los botones
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
        equipoDialog.getContentPane().setBackground(new Color(240, 248, 255));

        JPanel equipoPanel = new JPanel();
        equipoPanel.setLayout(new GridLayout(6, 1, 10, 10));
        equipoPanel.setBackground(new Color(240, 248, 255));
        equipoDialog.add(equipoPanel, BorderLayout.CENTER);

        ArrayList<Pokemon> equipo = EquipoDAO.listaPokemon();

        for (int i = 0; i < 6; i++) {
            String pokemonNombre = i < equipo.size() ? String.valueOf(equipo.get(i)) : "Empty Slot " + (i + 1);
            JButton pokemonButton = new JButton(pokemonNombre);
            pokemonButton.setFont(new Font("Pokemon GB", Font.PLAIN, 16));
            pokemonButton.setBackground(new Color(223, 230, 233));
            pokemonButton.setForeground(new Color(45, 52, 54));
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
        opcionesDialog.getContentPane().setBackground(new Color(240, 248, 255));

        JButton datosButton = new JButton("Datos");
        JButton objetoButton = new JButton("Objeto");
        JButton moverButton = new JButton("Mover");
        JButton atrasButton = new JButton("Atrás");

        JButton[] buttons = {datosButton, objetoButton, moverButton, atrasButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Pokemon GB", Font.PLAIN, 16));
            button.setBackground(new Color(223, 230, 233));
            button.setForeground(new Color(45, 52, 54));
            opcionesDialog.add(button);
        }

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
        cajasDialog.getContentPane().setBackground(new Color(240, 248, 255));

        String[] pokemonsEnCajas = obtenerPokemonsDesdeBD();

        for (String pokemonNombre : pokemonsEnCajas) {
            JButton pokemonButton = new JButton(pokemonNombre);
            pokemonButton.setFont(new Font("Pokemon GB", Font.PLAIN, 12));
            pokemonButton.setBackground(new Color(223, 230, 233));
            pokemonButton.setForeground(new Color(45, 52, 54));
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
        opcionesDialog.getContentPane().setBackground(new Color(240, 248, 255));

        JButton verDatosButton = new JButton("Ver datos");
        JButton sacarButton = new JButton("Sacar de la caja");
        JButton dejarButton = new JButton("Dejar en la caja");
        JButton moverButton = new JButton("Mover en las cajas");
        JButton atrasButton = new JButton("Atrás");

        JButton[] buttons = {verDatosButton, sacarButton, dejarButton, moverButton, atrasButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Pokemon GB", Font.PLAIN, 16));
            button.setBackground(new Color(223, 230, 233));
            button.setForeground(new Color(45, 52, 54));
            opcionesDialog.add(button);
        }

        verDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatosPC(opcionesDialog, pokemonNombre);
            }
        });

        sacarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacarDeCaja(opcionesDialog, pokemonNombre);
            }
        });

        dejarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dejarEnCaja(opcionesDialog, pokemonNombre);
            }
        });

        moverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverEnCajas(opcionesDialog, pokemonNombre);
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

    private static void mostrarDatosPC(JDialog parentDialog, String pokemonNombre) {
        Pokemon pokemon = obtenerDatosPokemonDesdeBD(pokemonNombre);
        JOptionPane.showMessageDialog(parentDialog,
                "Nombre: " + pokemonNombre + "\nHP: " + pokemon.HP + "\nAtaque: " + pokemon.ataque +
                        "\nDefensa: " + pokemon.defensa + "\nNivel: " + pokemon.nivel);
    }

    private static void sacarDeCaja(JDialog parentDialog, String pokemonNombre) {
        JOptionPane.showMessageDialog(parentDialog, "Funcionalidad para sacar " + pokemonNombre + " de la caja.");
    }

    private static void dejarEnCaja(JDialog parentDialog, String pokemonNombre) {
        JOptionPane.showMessageDialog(parentDialog, "Funcionalidad para dejar " + pokemonNombre + " en la caja.");
    }

    private static void moverEnCajas(JDialog parentDialog, String pokemonNombre) {
        JOptionPane.showMessageDialog(parentDialog, "Funcionalidad para mover " + pokemonNombre + " en las cajas.");
    }

    private static void sacarPokemon(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Funcionalidad para sacar Pokémon.");
    }

    private static void dejarPokemon(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Funcionalidad para dejar Pokémon.");
    }

    private static void moverPokemon(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Funcionalidad para mover Pokémon.");
    }

    private static void moverObjetos(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Funcionalidad para mover objetos.");
    }

    private static void desconectar(JFrame parentFrame) {
        int confirm = JOptionPane.showConfirmDialog(parentFrame, "¿Desea desconectarse?", "Desconectar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            parentFrame.dispose();
        }
    }

    private static Pokemon obtenerDatosPokemonDesdeBD(String nombrePokemon) {
        return PokemonDAO.leerDatos(Integer.parseInt(nombrePokemon)); // Datos de ejemplo
    }

    private static String obtenerObjetoDesdeBD(String nombrePokemon) {
        return "Poción"; // Objeto de ejemplo
    }

    private static String[] obtenerPokemonsDesdeBD() {
        return new String[]{"Pikachu", "Charmander", "Bulbasaur", "Squirtle", "Eevee"}; // Ejemplo de Pokémon en cajas
    }
}
