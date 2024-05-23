package interfaz;

import clases.*;
import utilidades.funcionesJSON;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PCPokemonGUI extends JFrame {
    private String usuario;

    public PCPokemonGUI(String usuario) {
        this.usuario = usuario;
        ejecutarInterfaz();
    }



    public static void main(String[] args) {
        usarFuentes();
        cargarPokemons();
        ejecutarInterfaz();
    }



    private static void ejecutarInterfaz() {
        JFrame frame = new JFrame("PC Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(new Color(0x003A70));

        JLabel titleLabel = new JLabel("PC de Pokémon", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("PokemonGb-RAeo", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(10, 1, 10, 10));
        mainMenuPanel.setBackground(Color.WHITE);
        frame.add(mainMenuPanel, BorderLayout.CENTER);

        JButton equipoButton = new JButton("Equipo");
        JButton cajasButton = new JButton("Cajas");
        JButton sacarPokemonButton = new JButton("Sacar Pokémon");
        JButton dejarPokemonButton = new JButton("Dejar Pokémon");
        JButton moverPokemonButton = new JButton("Mover Pokémon");
        JButton moverObjetosButton = new JButton("Mover Objetos");
        JButton desconectarButton = new JButton("Desconectar");

        equipoButton.setForeground(Color.BLACK);
        cajasButton.setForeground(Color.BLACK);
        sacarPokemonButton.setForeground(Color.BLACK);
        dejarPokemonButton.setForeground(Color.BLACK);
        moverPokemonButton.setForeground(Color.BLACK);
        moverObjetosButton.setForeground(Color.BLACK);
        desconectarButton.setForeground(Color.BLACK);

        Insets margen = new Insets(50, 50, 50, 50);
        equipoButton.setMargin(margen);
        cajasButton.setMargin(margen);
        sacarPokemonButton.setMargin(margen);
        dejarPokemonButton.setMargin(margen);
        moverPokemonButton.setMargin(margen);
        moverObjetosButton.setMargin(margen);
        desconectarButton.setMargin(margen);

        equipoButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        cajasButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        sacarPokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        dejarPokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        moverPokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        moverObjetosButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        desconectarButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

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
                try {
                    mostrarEquipo(frame);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
                    mostrarCajas(frame);
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

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }



    static void usarFuentes() {
        GraphicsEnvironment GE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.util.List<String> AVAILABLE_FONT_FAMILY_NAMES = Arrays.asList(GE.getAvailableFontFamilyNames());
        try {
            List<File> LIST = Arrays.asList(
                    new File("fuentes/PokemonGb-RAeo.ttf")
            );
            for (File LIST_ITEM : LIST) {
                if (LIST_ITEM.exists()) {
                    Font FONT = Font.createFont(Font.TRUETYPE_FONT, LIST_ITEM);
                    if (!AVAILABLE_FONT_FAMILY_NAMES.contains(FONT.getFontName())) {
                        GE.registerFont(FONT);
                    }
                }
            }
        } catch (FontFormatException | IOException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }



    private static void cargarPokemons() {
        try {
            ArrayList<Pokemon> pokemons = PokemonDAO.instanciarTodosLosPokemon();
            ArrayList<Pokemon> equipo = new ArrayList<>();
            ArrayList<Pokemon> caja = new ArrayList<>();

            for (Pokemon pokemon : pokemons) {
                if (pokemon.isEstaEnEquipo()) {
                    equipo.add(pokemon);
                } else if (pokemon.isEstaEnCaja()) {
                    caja.add(pokemon);
                }
            }

            EquipoDAO.setEquipo(equipo);
            CajaDAO.setCaja(caja);
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los equipos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private static void actualizarVistaEquipo (JDialog equipoDialog, JPanel equipoPanel) throws IOException {
        equipoPanel.removeAll();
        equipoPanel.setLayout(new GridLayout(6, 1, 10, 10));

        Equipo equipo;
        try {
            equipo = funcionesJSON.leerEquipoDeJSON("json/equipo.json");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(equipoDialog, "Error al leer el equipo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (equipo != null && equipo.getEquipo() != null && !equipo.getEquipo().isEmpty()) {
            for (Pokemon pokemon : equipo.getEquipo()) {
                if (pokemon != null) {
                    String pokemonNombre = pokemon.getNombre();
                    if (pokemonNombre != null) {
                        JButton pokemonButton = new JButton(pokemonNombre);
                        pokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

                        String imgPokemonPath = "pokemonImagenes/" + pokemonNombre.toLowerCase() + ".png";
                        ImageIcon imgPokemon = new ImageIcon(imgPokemonPath);
                        Image scaledImage = imgPokemon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        pokemonButton.setIcon(new ImageIcon(scaledImage));
                        equipoPanel.add(pokemonButton);

                        pokemonButton.addActionListener(e -> {
                            if (!pokemonNombre.startsWith("Empty")) {
                                try {
                                    mostrarOpcionesPokemon(equipoDialog, pokemon);
                                    actualizarVistaEquipo(equipoDialog, equipoPanel); // Refrescar la vista después de realizar cambios
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else {
                                JOptionPane.showMessageDialog(equipoDialog, "No hay Pokémon en este slot.");
                            }
                        });
                    } else {
                        System.err.println("Advertencia: Se encontró un Pokémon con nombre nulo.");
                    }
                } else {
                    System.err.println("Advertencia: Se encontró un objeto Pokémon nulo.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(equipoDialog, "El equipo está vacío.");
        }

        equipoPanel.revalidate();
        equipoPanel.repaint();
    }



    private static void mostrarEquipo(JFrame parentFrame) throws IOException {
        JDialog equipoDialog = new JDialog(parentFrame, "Equipo", true);
        equipoDialog.setSize(600, 500);
        equipoDialog.setLayout(new BorderLayout());
        equipoDialog.setResizable(true);
        equipoDialog.setLocationRelativeTo(null);

        JPanel equipoPanel = new JPanel();
        equipoDialog.add(equipoPanel, BorderLayout.CENTER);

        actualizarVistaEquipo(equipoDialog, equipoPanel);

        equipoDialog.setVisible(true);

        System.out.println("Equipo cargado.");
    }



    private static void mostrarOpcionesPokemon(JDialog parentDialog, Pokemon pokemon) {
        JDialog opcionesDialog = new JDialog(parentDialog, pokemon.getNombre(), true);
        opcionesDialog.setSize(500, 400);
        opcionesDialog.setLayout(new GridLayout(4, 1, 10, 10));
        opcionesDialog.setResizable(true);
        opcionesDialog.setLocationRelativeTo(null);
        opcionesDialog.getContentPane().setBackground(new Color(0x003A70));

        JButton datosButton = new JButton("Datos");
        JButton objetoButton = new JButton("Objeto");
        JButton moverButton = new JButton("Mover");
        JButton atrasButton = new JButton("Atrás");

        datosButton.setForeground(Color.BLACK);
        objetoButton.setForeground(Color.BLACK);
        moverButton.setForeground(Color.BLACK);
        atrasButton.setForeground(Color.BLACK);

        datosButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        objetoButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        moverButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        atrasButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

        opcionesDialog.add(datosButton);
        opcionesDialog.add(objetoButton);
        opcionesDialog.add(moverButton);
        opcionesDialog.add(atrasButton);

        datosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarDatos(opcionesDialog, pokemon);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        objetoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarObjeto(opcionesDialog, pokemon);
            }
        });

        moverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    moverPokemonEquipo(opcionesDialog, pokemon);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
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



    private static void mostrarDatos(JDialog parentDialog, Pokemon pokemon) throws SQLException, IOException {
        Pokemon pokemonAux = PokemonDAO.buscarPokemonPorNombre(pokemon.getNombre());
        JOptionPane.showMessageDialog(parentDialog,
                "Nombre: " + pokemonAux.getNombre() + "\n" +
                        "Nivel: " + pokemonAux.getNivel() + "\n" +
                        "Habilidad: " + pokemonAux.getHabilidad() + "\n" +
                        "Objeto: " + pokemonAux.getObjeto() + "\n" +
                        "PS: " + pokemonAux.getHp() + "\n" +
                        "Ataque: " + pokemonAux.getAtaque() + "\n" +
                        "Defensa: " + pokemonAux.getDefensa() + "\n" +
                        "Velocidad: " + pokemonAux.getVelocidad() + "\n" +
                        "Ataque Especial: " + pokemonAux.getAtaqueEspecial() + "\n" +
                        "Defensa Especial: " + pokemonAux.getDefensaEspecial());
    }



    private static void mostrarObjeto(JDialog parentDialog, Pokemon pokemon) {
        String objeto = obtenerObjetoDesdeBD(pokemon);
        if (objeto == null || objeto.isEmpty()) {
            JOptionPane.showMessageDialog(parentDialog, pokemon.getNombre() + " no porta ningún objeto.");
        } else {
            JOptionPane.showMessageDialog(parentDialog, pokemon.getNombre() + " porta el objeto: " + objeto);
        }
    }



    private static void moverPokemonEquipo(JDialog parentDialog, Pokemon pokemon) throws SQLException, IOException {
        EquipoDAO.agregarPokemon(pokemon);
        JOptionPane.showMessageDialog(parentDialog, "Funcionalidad para mover " + pokemon.getNombre() + " en el equipo.");
    }



    private static void actualizarVistaCajas (JDialog cajasDialog) throws IOException {
        cajasDialog.getContentPane().removeAll();
        cajasDialog.setLayout(new GridLayout(5, 10, 5, 5));

        ArrayList<Pokemon> pokemonsEnCaja = CajaDAO.listaPokemon();

        for (Pokemon pokemon : pokemonsEnCaja) {
            JButton pokemonButton = new JButton(pokemon.getNombre());
            pokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 12));
            String imgPokemonPath = "pokemonImagenes/" + pokemon.getNombre().toLowerCase() + ".png";
            ImageIcon imgPokemon = new ImageIcon(imgPokemonPath);
            Image scaledImage = imgPokemon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            pokemonButton.setIcon(new ImageIcon(scaledImage));
            cajasDialog.add(pokemonButton);

            pokemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mostrarOpcionesPC(cajasDialog, pokemon);
                        actualizarVistaCajas(cajasDialog);
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
        cajasDialog.revalidate();
        cajasDialog.repaint();
    }



    private static void mostrarCajas(JFrame parentFrame) throws SQLException, IOException {
        JDialog cajasDialog = new JDialog(parentFrame, "Cajas", true);
        cajasDialog.setSize(800, 500);
        cajasDialog.setLocationRelativeTo(null);

        actualizarVistaCajas(cajasDialog);

        cajasDialog.setVisible(true);
    }



    private static void mostrarOpcionesPC(JDialog parentDialog, Pokemon pokemon) throws SQLException, IOException {
        JDialog opcionesDialog = new JDialog(parentDialog, pokemon.getNombre(), true);
        opcionesDialog.setSize(500, 400);
        opcionesDialog.setLayout(new GridLayout(5, 1, 10, 10));
        opcionesDialog.setLocationRelativeTo(null);
        opcionesDialog.getContentPane().setBackground(new Color(0x003A70));

        JButton datosButton = new JButton("Datos");
        JButton sacarPokemonButton = new JButton("Sacar Pokémon");
        JButton cambiarApodoButton = new JButton("Cambiar Apodo");
        JButton liberarButton = new JButton("Liberar");
        JButton salirButton = new JButton("Salir");

        datosButton.setForeground(Color.BLACK);
        sacarPokemonButton.setForeground(Color.BLACK);
        cambiarApodoButton.setForeground(Color.BLACK);
        liberarButton.setForeground(Color.BLACK);
        salirButton.setForeground(Color.BLACK);

        datosButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        sacarPokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        cambiarApodoButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        liberarButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        salirButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

        opcionesDialog.add(datosButton);
        opcionesDialog.add(cambiarApodoButton);
        opcionesDialog.add(liberarButton);
        opcionesDialog.add(sacarPokemonButton);
        opcionesDialog.add(salirButton);

        datosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarDatos(opcionesDialog, pokemon);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        sacarPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sacarPokemonDeBD(pokemon);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                opcionesDialog.dispose();
            }
        });

        cambiarApodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog(opcionesDialog, "Introduce el nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    try {
                        cambiarApodoEnBD(pokemon, nuevoNombre);
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    opcionesDialog.dispose();
                }
            }
        });

        liberarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(opcionesDialog, "¿Estás seguro de liberar " + pokemon.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        liberarPokemonDeBD(pokemon);
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
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



    private static void actualizarVistaDejar(JDialog dejarDialog) throws SQLException, IOException {
        dejarDialog.getContentPane().removeAll();
        dejarDialog.setLayout(new GridLayout(6, 1, 10, 10));

        ArrayList<Pokemon> equipo = EquipoDAO.getEquipo().getEquipo();

        for (Pokemon pokemon : equipo) {
            JButton pokemonButton = new JButton(pokemon.getNombre());
            pokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

            String imgPokemonPath = "pokemonImagenes/" + pokemon.getNombre().toLowerCase() + ".png";
            ImageIcon imgPokemon = new ImageIcon(imgPokemonPath);
            Image scaledImage = imgPokemon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            pokemonButton.setIcon(new ImageIcon(scaledImage));

            dejarDialog.add(pokemonButton);

            pokemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mostrarOpcionesDejar(dejarDialog, pokemon);
                        actualizarVistaDejar(dejarDialog);
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        dejarDialog.revalidate();
        dejarDialog.repaint();
    }



    private static void dejarPokemon(JFrame parentFrame) throws SQLException, IOException {
        JDialog dejarDialog = new JDialog(parentFrame, "Dejar Pokémon", true);
        dejarDialog.setSize(500, 400);
        dejarDialog.setLocationRelativeTo(null);

        actualizarVistaDejar(dejarDialog);

        dejarDialog.setVisible(true);
    }



    private static void mostrarOpcionesDejar(JDialog parentDialog, Pokemon pokemon) throws SQLException, IOException {
        JDialog opcionesDialog = new JDialog(parentDialog, pokemon.getNombre(), true);
        opcionesDialog.setSize(500, 400);
        opcionesDialog.setLayout(new GridLayout(5, 1, 10, 10));
        opcionesDialog.setLocationRelativeTo(null);
        opcionesDialog.getContentPane().setBackground(new Color(0x003A70));

        JButton dejarButton = new JButton("Dejar");
        JButton datosButton = new JButton("Datos");
        JButton cambiarApodoButton = new JButton("Cambiar Apodo");
        JButton liberarButton = new JButton("Liberar");
        JButton salirButton = new JButton("Salir");

        dejarButton.setForeground(Color.BLACK);
        datosButton.setForeground(Color.BLACK);
        cambiarApodoButton.setForeground(Color.BLACK);
        liberarButton.setForeground(Color.BLACK);
        salirButton.setForeground(Color.BLACK);

        dejarButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        datosButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        cambiarApodoButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        liberarButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        salirButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

        opcionesDialog.add(dejarButton);
        opcionesDialog.add(datosButton);
        opcionesDialog.add(cambiarApodoButton);
        opcionesDialog.add(liberarButton);
        opcionesDialog.add(salirButton);

        dejarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dejarPokemonEnBD(pokemon);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                opcionesDialog.dispose();
            }
        });

        datosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarDatos(opcionesDialog, pokemon);
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
                    try {
                        cambiarApodoEnBD(pokemon, nuevoNombre);
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    opcionesDialog.dispose();
                }
            }
        });

        liberarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(opcionesDialog, "¿Estás seguro de liberar " + pokemon.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        liberarPokemonDeBD(pokemon);
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
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



    private static void actualizarVistaMover(JDialog moverDialog, ArrayList<Pokemon> listaEquipo) {
        moverDialog.getContentPane().removeAll();
        moverDialog.setLayout(new GridLayout(3, 1, 10, 10));

        String[] nombresEquipo = listaEquipo.stream().map(Pokemon::getNombre).toArray(String[]::new);

        JComboBox<String> pokemonComboBox = new JComboBox<>(nombresEquipo);
        pokemonComboBox.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

        JButton equipoButton = new JButton("Mover desde equipo");
        JButton pcButton = new JButton("Mover desde PC");

        equipoButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        pcButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

        moverDialog.add(pokemonComboBox);
        moverDialog.add(equipoButton);
        moverDialog.add(pcButton);

        equipoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        pcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombrePokemonSeleccionado = (String) pokemonComboBox.getSelectedItem();
                if (nombrePokemonSeleccionado != null) {
                    Pokemon pokemonSeleccionado = listaEquipo.stream().filter(p -> p.getNombre().equals(nombrePokemonSeleccionado)).findFirst().orElse(null);
                    Pokemon pokemonASacar = null;
                    try {
                        pokemonASacar = obtenerPokemonASacarDelEquipo(moverDialog, listaEquipo);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (pokemonASacar != null) {
                        try {
                            dejarPokemonEnBD(pokemonSeleccionado);
                            listaEquipo.remove(pokemonSeleccionado);
                            listaEquipo.add(pokemonASacar);
                            pokemonComboBox.setModel(new DefaultComboBoxModel<>(listaEquipo.stream().map(Pokemon::getNombre).toArray(String[]::new)));

                            EquipoDAO.quitarPokemon(pokemonSeleccionado);
                            CajaDAO.meterPokemonACaja(pokemonASacar.getNombre());
                        } catch (SQLException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        actualizarVistaMover(moverDialog, listaEquipo);
                    }
                } else {
                    JOptionPane.showMessageDialog(moverDialog, "Error: Pokémon no seleccionado.");
                }
            }
        });

        moverDialog.revalidate();
        moverDialog.repaint();
    }



    private static Pokemon obtenerPokemonASacarDelEquipo(JDialog moverDialog, ArrayList<Pokemon> listaEquipo) throws IOException {
        ArrayList<Pokemon> listaCaja = CajaDAO.listaPokemon();

        String[] nombresEquipo = listaEquipo.stream().map(Pokemon::getNombre).toArray(String[]::new);
        String[] nombresCaja = listaCaja.stream().map(Pokemon::getNombre).toArray(String[]::new);

        JComboBox<String> equipoComboBox = new JComboBox<>(nombresEquipo);
        JComboBox<String> cajaComboBox = new JComboBox<>(nombresCaja);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Selecciona el Pokémon del equipo:"));
        panel.add(equipoComboBox);
        panel.add(new JLabel("Selecciona el Pokémon de la caja:"));
        panel.add(cajaComboBox);

        int result = JOptionPane.showConfirmDialog(moverDialog, panel, "Seleccionar Pokémon", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombrePokemonEquipo = (String) equipoComboBox.getSelectedItem();
            String nombrePokemonCaja = (String) cajaComboBox.getSelectedItem();
            if (nombrePokemonEquipo != null && nombrePokemonCaja != null) {
                Pokemon pokemonEquipo = listaEquipo.stream().filter(p -> p.getNombre().equals(nombrePokemonEquipo)).findFirst().orElse(null);
                Pokemon pokemonCaja = listaCaja.stream().filter(p -> p.getNombre().equals(nombrePokemonCaja)).findFirst().orElse(null);
                if (pokemonEquipo != null && pokemonCaja != null) {
                    try {
                        listaEquipo.remove(pokemonEquipo);
                        listaEquipo.add(pokemonCaja);
                        listaCaja.remove(pokemonCaja);

                        sacarPokemonDeBD(pokemonCaja);
                        dejarPokemonEnBD(pokemonEquipo);

                        EquipoDAO.setEquipo(listaEquipo);
                        CajaDAO.setCaja(listaCaja);
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(moverDialog, "Error: No se encontró el Pokémon seleccionado.");
                }
            } else {
                JOptionPane.showMessageDialog(moverDialog, "Error: Selecciona un Pokémon del equipo y de la caja.");
            }
        }
        return listaEquipo.stream().filter(p -> p.getNombre().equals((String) equipoComboBox.getSelectedItem())).findFirst().orElse(null);
    }



    private static void moverPokemon(JFrame parentFrame) throws SQLException, IOException {
        JDialog moverDialog = new JDialog(parentFrame, "Mover Pokémon", true);
        moverDialog.setSize(800, 500);
        moverDialog.setLocationRelativeTo(null);

        ArrayList<Pokemon> listaEquipo = EquipoDAO.getEquipo().getEquipo();

        actualizarVistaMover(moverDialog, listaEquipo);

        moverDialog.setVisible(true);
    }



    private static void actualizarVistaCambiarObjeto(JDialog cambiarObjetoDialog, ArrayList<Pokemon> listaEquipo) {
        cambiarObjetoDialog.getContentPane().removeAll();
        cambiarObjetoDialog.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel pokemonLabel = new JLabel("Dime el Pokemon a cambiar el Objeto:");
        JTextField pokemonTextField = new JTextField();

        JLabel objetoLabel = new JLabel("Dime el nuevo objeto:");
        JTextField objetoTextField = new JTextField();

        JButton cambiarObjetoButton = new JButton("Cambiar Objeto");

        pokemonLabel.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        pokemonTextField.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        objetoLabel.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        objetoTextField.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        cambiarObjetoButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

        cambiarObjetoDialog.add(pokemonLabel);
        cambiarObjetoDialog.add(pokemonTextField);
        cambiarObjetoDialog.add(objetoLabel);
        cambiarObjetoDialog.add(objetoTextField);
        cambiarObjetoDialog.add(cambiarObjetoButton);

        cambiarObjetoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombrePokemon = pokemonTextField.getText();
                String nuevoObjeto = objetoTextField.getText();
                PokemonDAO pk = null;

                if (nombrePokemon.isEmpty() || nuevoObjeto.isEmpty()) {
                    JOptionPane.showMessageDialog(cambiarObjetoDialog, "Error: Pokémon o objeto no especificado.");
                    return;
                }

                Pokemon pokemonACambiar = listaEquipo.stream()
                        .filter(p -> p.getNombre().equalsIgnoreCase(nombrePokemon))
                        .findFirst()
                        .orElse(null);

                if (pokemonACambiar != null) {
                    try {
                        pk.cambiarObjetoPokemon(pokemonACambiar, nuevoObjeto);
                        JOptionPane.showMessageDialog(cambiarObjetoDialog, "Objeto cambiado exitosamente.");
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(cambiarObjetoDialog, "Error: Pokémon no encontrado en el equipo.");
                }
            }
        });

        cambiarObjetoDialog.revalidate();
        cambiarObjetoDialog.repaint();
    }



    private static void moverObjetos(JFrame parentFrame) throws SQLException, IOException {
        JDialog moverDialog = new JDialog(parentFrame, "Mover Objetos", true);
        moverDialog.setSize(800, 500);
        moverDialog.setLocationRelativeTo(null);

        ArrayList<Pokemon> listaEquipo = EquipoDAO.getEquipo().getEquipo();
        actualizarVistaCambiarObjeto(moverDialog, listaEquipo);

        moverDialog.setVisible(true);
    }



    private static String obtenerObjetoDesdeBD(Pokemon pokemon) {
        return PokemonDAO.devolverObjetoPokemon(pokemon);
    }



    private static void sacarPokemonDeBD(Pokemon pokemon) throws SQLException, IOException {
        if (EquipoDAO.equipoLleno(EquipoDAO.getEquipo().getEquipo())) {
            JOptionPane.showMessageDialog(null, "El equipo está lleno.");
            return;
        }

        CajaDAO.sacarPokemonCaja(pokemon.getNombre());
        EquipoDAO.agregarPokemon(pokemon);

        Equipo equipo = EquipoDAO.getEquipo();
        Caja caja = CajaDAO.getCaja();

        funcionesJSON.escribirEquipoAJSON(equipo, "json/equipo.json");
        funcionesJSON.escribirCajaAJSON(caja, "json/caja.json");
    }



    private static void dejarPokemonEnBD(Pokemon pokemon) throws SQLException, IOException {
        try {
            Equipo equipo = EquipoDAO.getEquipo();

            if (pokemon != null) {
                ArrayList<Pokemon> listaEquipo = equipo.getEquipo();

                boolean eliminado = listaEquipo.removeIf(p -> p.getNombre().equalsIgnoreCase(pokemon.getNombre()));

                if (eliminado) {
                    equipo.setEquipo(listaEquipo);
                    funcionesJSON.escribirEquipoAJSON(equipo, "json/equipo.json");
                    JOptionPane.showMessageDialog(null, pokemon.getNombre() + " ha sido transferido correctamente a la caja");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha encontrado ningún Pokémon con el nombre en el equipo");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha proporcionado ningún Pokémon válido para transferir");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al intentar transferir el Pokémon: pokemonNombre");
        }
    }



    private static void cambiarApodoEnBD(Pokemon pokemon, String nuevoNombre) throws SQLException, IOException {
        PokemonDAO.cambiarApodoPokemon(pokemon, nuevoNombre);
    }



    private static boolean liberarPokemonDeBD(Pokemon pokemon) throws SQLException, IOException {
        CajaDAO.liberarPokemon(pokemon);
        return true;
    }



    private static void desconectar(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Desconectando y guardando cambios...");
        System.exit(0);
    }
}