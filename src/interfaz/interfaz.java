package interfaz;

import clases.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

class prueba extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("PC Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("PC de Pokémon", SwingConstants.CENTER);
        titleLabel.setFont(new Font("PokemonGb-RAeo", Font.BOLD, 24));
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
                try {
                    mostrarCajas(frame);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        sacarPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sacarPokemon(frame);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        dejarPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dejarPokemon(frame);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        moverPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    moverPokemon(frame);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        moverObjetosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    moverObjetos(frame);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
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

        ArrayList<Pokemon> equipo;
        try {
            equipo = EquipoDAO.getEquipo().getEquipo();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 6; i++) {
            String pokemonNombre = i < equipo.size() ? String.valueOf(equipo.get(i)) : "Empty Slot " + (i + 1);
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
                try {
                    mostrarDatos(opcionesDialog, pokemonNombre);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
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

    private static void mostrarDatos(JDialog parentDialog, String pokemonNombre) throws SQLException, IOException {
        Pokemon pokemon = obtenerDatosPokemonDesdeBD(pokemonNombre);
        JOptionPane.showMessageDialog(parentDialog,
                "Nombre: " + pokemonNombre + "\nHP: " + pokemon.Hp + "\nAtaque: " + pokemon.Ataque +
                        "\nDefensa: " + pokemon.Defensa + "\nNivel: " + pokemon.Nivel);
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

    private static void mostrarCajas(JFrame parentFrame) throws SQLException, IOException {
        JDialog cajasDialog = new JDialog(parentFrame, "Cajas", true);
        cajasDialog.setSize(500, 400);
        cajasDialog.setLayout(new GridLayout(5, 10, 5, 5));

        String[] pokemonsEnCaja = obtenerPokemonsDesdeBD();

        for (String pokemonNombre : pokemonsEnCaja) {
            JButton pokemonButton = new JButton(pokemonNombre);
            pokemonButton.setFont(new Font("Arial", Font.PLAIN, 12));
            cajasDialog.add(pokemonButton);

            pokemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mostrarOpcionesPC(cajasDialog, pokemonNombre);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        cajasDialog.setVisible(true);
    }

    private static void mostrarOpcionesPC(JDialog parentDialog, String pokemonNombre) throws SQLException, IOException {
        ArrayList<Pokemon> listaEquipo = EquipoDAO.getEquipo().getEquipo();
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
                if (!EquipoDAO.equipoLleno(listaEquipo)) {
                    Pokemon pokemonASacar = null;
                    for (Pokemon pokemon : listaEquipo) {
                        if (pokemon.getNombre().equals(pokemonNombre)) {
                            pokemonASacar = pokemon;
                            break;
                        }
                    }

                    if (pokemonASacar != null) {
                        try {
                            sacarPokemonDeBD(pokemonASacar);
                        } catch (SQLException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        opcionesDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(opcionesDialog, pokemonNombre + " no está en el equipo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(opcionesDialog, "El equipo está lleno.");
                }
            }
        });

        datosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarDatos(opcionesDialog, pokemonNombre);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cambiarApodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog(opcionesDialog, "Introduce el nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    Pokemon pokemonACambiar = null;
                    for (Pokemon pokemon : listaEquipo) {
                        if (pokemon.getNombre().equals(pokemonNombre)) {
                            pokemonACambiar = pokemon;
                            break;
                        }
                    }

                    if (pokemonACambiar != null) {
                        try {
                            cambiarApodoEnBD(pokemonACambiar, nuevoNombre);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        opcionesDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(opcionesDialog, pokemonNombre + " no está en el equipo.");
                    }
                }
            }
        });

        liberarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(opcionesDialog, "¿Estás seguro de liberar " + pokemonNombre + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (liberarPokemonDeBD(pokemonNombre)) {
                        opcionesDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(opcionesDialog, "Error al liberar el Pokémon.");
                    }
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

    private static void sacarPokemon(JFrame parentFrame) throws SQLException, IOException {
        CajaDAO.sacarPokemonCaja("Caterpie");
        mostrarCajas(parentFrame);
    }

    private static void dejarPokemon(JFrame parentFrame) throws SQLException, IOException {
        JDialog dejarDialog = new JDialog(parentFrame, "Dejar Pokémon", true);
        dejarDialog.setSize(400, 300);
        dejarDialog.setLayout(new GridLayout(6, 1, 10, 10));

        ArrayList<Pokemon> equipo = EquipoDAO.getEquipo().getEquipo();

        for (Pokemon pokemonNombre : equipo) {
            JButton pokemonButton = new JButton(String.valueOf(pokemonNombre));
            pokemonButton.setFont(new Font("Arial", Font.PLAIN, 16));
            dejarDialog.add(pokemonButton);

            pokemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mostrarOpcionesDejar(dejarDialog, String.valueOf(pokemonNombre));
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        dejarDialog.setVisible(true);
    }

    private static void mostrarOpcionesDejar(JDialog parentDialog, String pokemonNombre) throws SQLException, IOException {
        ArrayList<Pokemon> listaEquipo = EquipoDAO.getEquipo().getEquipo();
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
                try {
                    mostrarDatos(opcionesDialog, pokemonNombre);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cambiarApodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog(opcionesDialog, "Introduce el nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    Pokemon pokemonACambiar = null;
                    for (Pokemon pokemon : listaEquipo) {
                        if (pokemon.getNombre().equals(pokemonNombre)) {
                            pokemonACambiar = pokemon;
                            break;
                        }
                    }

                    if (pokemonACambiar != null) {
                        try {
                            cambiarApodoEnBD(pokemonACambiar, nuevoNombre);
                        } catch (SQLException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        opcionesDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(opcionesDialog, pokemonNombre + " no está en el equipo.");
                    }
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

    private static void moverPokemon(JFrame parentFrame) throws SQLException, IOException {
        JDialog moverDialog = new JDialog(parentFrame, "Mover Pokémon", true);
        moverDialog.setSize(600, 400);
        moverDialog.setLayout(new GridLayout(3, 1, 10, 10));

        ArrayList<Pokemon> listaEquipo = EquipoDAO.getEquipo().getEquipo();
        ArrayList<String> nombres = new ArrayList<>();
        for (Pokemon pokemon : listaEquipo) {
            nombres.add(pokemon.getNombre());
        }
        JComboBox<String> pokemonComboBox = new JComboBox<>(nombres.toArray(new String[0]));
        pokemonComboBox.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton equipoButton = new JButton("Mover desde equipo");
        JButton pcButton = new JButton("Mover desde PC");

        equipoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        pcButton.setFont(new Font("Arial", Font.PLAIN, 16));

        moverDialog.add(pokemonComboBox);
        moverDialog.add(equipoButton);
        moverDialog.add(pcButton);

        equipoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pokemonNombre = (String) pokemonComboBox.getSelectedItem();

                Pokemon pokemonASacar = null;
                for (Pokemon pokemon : listaEquipo) {
                    if (pokemon.getNombre().equals(pokemonNombre)) {
                        pokemonASacar = pokemon;
                        break;
                    }
                }

                if (pokemonASacar != null) {
                    try {
                        sacarPokemonDeBD(pokemonASacar);
                        listaEquipo.remove(pokemonASacar);
                        listaEquipo.remove(pokemonASacar.getNombre());
                        pokemonComboBox.setModel(new DefaultComboBoxModel<>(listaEquipo.toArray(new String[0])));
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(moverDialog, "Error: Pokémon no encontrado.");
                }
            }
        });

        pcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pokemonNombre = (String) pokemonComboBox.getSelectedItem();
                dejarPokemonEnBD(pokemonNombre);
                Pokemon pokemonAnadido = CajaDAO.obtenerPokemonDeCaja(pokemonNombre);
                listaEquipo.add(pokemonAnadido);
                listaEquipo.add(pokemonAnadido);
                pokemonComboBox.setModel(new DefaultComboBoxModel<>(listaEquipo.toArray(new String[0])));
            }
        });
        moverDialog.setVisible(true);
    }

    private static void moverObjetos(JFrame parentFrame) throws SQLException, IOException {
        JDialog moverDialog = new JDialog(parentFrame, "Mover Objetos", true);
        moverDialog.setSize(600, 400);
        moverDialog.setLayout(new GridLayout(3, 2, 10, 10));

        Equipo pokemonList = EquipoDAO.getEquipo();

        JComboBox<String> pokemonComboBox = new JComboBox<>((ComboBoxModel) pokemonList);
        pokemonComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        moverDialog.add(pokemonComboBox);

        JLabel objetoLabel = new JLabel("Selecciona el objeto:");
        objetoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        moverDialog.add(objetoLabel);

        String[] objetos = {"Objeto 1", "Objeto 2"};
        JComboBox<String> objetoComboBox = new JComboBox<>(objetos);
        objetoComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        moverDialog.add(objetoComboBox);

        JButton moverButton = new JButton("Mover");
        moverButton.setFont(new Font("Arial", Font.PLAIN, 16));
        moverDialog.add(moverButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setFont(new Font("Arial", Font.PLAIN, 16));
        moverDialog.add(cancelarButton);

        moverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pokemonNombre = (String) pokemonComboBox.getSelectedItem();
                String objeto = (String) objetoComboBox.getSelectedItem();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverDialog.dispose();
            }
        });

        moverDialog.setVisible(true);
    }

    private static void desconectar(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Desconectando y guardando cambios...");
        System.exit(0);
    }

    private static Equipo obtenerEquipoDesdeBD() {
        try {
            return EquipoDAO.getEquipo();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el equipo: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage());
            return null;
        }
    }

    private static String[] obtenerPokemonsDesdeBD() throws SQLException, IOException {
        ArrayList<Pokemon> equipo = EquipoDAO.getEquipo().getEquipo();
        String[] listaPokemon = new String[equipo.size()];
        for (int i = 0; i < equipo.size(); i++) {
            listaPokemon[i] = equipo.get(i).getNombre();
        }
        return listaPokemon;
    }

    private static Pokemon obtenerDatosPokemonDesdeBD(String pokemonNombre) throws SQLException, IOException {
        return PokemonDAO.buscarPokemonPorNombre(pokemonNombre);
    }

    private static String obtenerObjetoDesdeBD(String pokemonNombre) {
        return PokemonDAO.devolverObjetoPokemon(pokemonNombre);
    }

    private static void sacarPokemonDeBD(Pokemon pokemon) throws SQLException, IOException {
        if (EquipoDAO.equipoLleno(EquipoDAO.getEquipo().getEquipo())) {
            JOptionPane.showMessageDialog(null, "El equipo está lleno.");
            return;
        }

        CajaDAO.sacarPokemonCaja(pokemon.getNombre());
        EquipoDAO.agregarPokemon(pokemon);
        CajaDAO.meterPokemonACaja(pokemon.getNombre());

    }

    private static void dejarPokemonEnBD(String pokemonNombre) {
        CajaDAO.meterPokemonACaja(pokemonNombre);
    }

    private static void cambiarApodoEnBD(Pokemon pokemon, String nuevoNombre) throws SQLException, IOException {
        PokemonDAO.cambiarApodoPokemon(pokemon, nuevoNombre);
    }

    private static boolean liberarPokemonDeBD(String pokemonNombre) {
        CajaDAO.liberarPokemon(pokemonNombre);
        return true;
    }
}
