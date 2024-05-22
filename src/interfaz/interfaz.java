package interfaz;

import clases.*;
import utilidades.funcionesJSON;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

class PCPokemonGUI extends JFrame {
    private String usuario;

    public PCPokemonGUI(String usuario) {
        this.usuario = usuario;
        ejecutarInterfaz();
    }

    private static void ejecutarInterfaz() {
        cargarPokemons();
        JFrame frame = new JFrame("PC Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(true);
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

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ejecutarInterfaz();
    }

    private static void cargarPokemons() {
        try {
            ArrayList<Pokemon> pokemons = PokemonDAO.seleccionarTodosLosPokemon();
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los equipos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void mostrarEquipo(JFrame parentFrame) throws IOException {
        JDialog equipoDialog = new JDialog(parentFrame, "Equipo", true);
        equipoDialog.setSize(500, 400);
        equipoDialog.setLayout(new BorderLayout());

        JPanel equipoPanel = new JPanel();
        equipoPanel.setLayout(new GridLayout(6, 1, 10, 10));
        equipoDialog.add(equipoPanel, BorderLayout.CENTER);

        Equipo equipo;
        try {
            equipo = funcionesJSON.leerEquipoDeJSON("json/equipo.json");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame, "Error al leer el equipo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (equipo != null && equipo.getEquipo() != null && !equipo.getEquipo().isEmpty()) {
            for (Pokemon pokemon : equipo.getEquipo()) {
                if (pokemon != null) {
                    String pokemonNombre = pokemon.getNombre();
                    if (pokemonNombre != null) {
                        JButton pokemonButton = new JButton(pokemonNombre);
                        pokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

                        //Cargar imagen
                        String imgPokemonPath = "pokemonImagenes/" + pokemonNombre.toLowerCase() + ".png";
                        ImageIcon imgPokemon = new ImageIcon(imgPokemonPath);
                        Image scaledImage = imgPokemon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        pokemonButton.setIcon(new ImageIcon(scaledImage));
                        equipoPanel.add(pokemonButton);

                        pokemonButton.addActionListener(e -> {
                            if (!pokemonNombre.startsWith("Empty")) {
                                mostrarOpcionesPokemon(equipoDialog, pokemon);
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
        equipoDialog.setVisible(true);

        System.out.println("Equipo cargado: " + equipo);
        assert equipo != null;
        for (Pokemon pokemon : equipo.getEquipo()) {
            System.out.println("Pokemon: " + pokemon);
        }
    }

    private static void mostrarOpcionesPokemon(JDialog parentDialog, Pokemon pokemon) {
        JDialog opcionesDialog = new JDialog(parentDialog, pokemon.getNombre(), true);
        opcionesDialog.setSize(400, 300);
        opcionesDialog.setLayout(new GridLayout(4, 1, 10, 10));

        JButton datosButton = new JButton("Datos");
        JButton objetoButton = new JButton("Objeto");
        JButton moverButton = new JButton("Mover");
        JButton atrasButton = new JButton("Atrás");

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
                    mostrarDatos(opcionesDialog, pokemon.getNombre());
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

    private static void mostrarDatos(JDialog parentDialog, String pokemonNombre) throws SQLException, IOException {
        Pokemon pokemon = obtenerDatosPokemonDesdeBD(pokemonNombre);
        JOptionPane.showMessageDialog(parentDialog,
                "Nombre: " + pokemonNombre + "\n" +
                        "Nivel: " + pokemon.getNivel() + "\n" +
                        "Habilidad: " + pokemon.getHabilidad() + "\n" +
                        "Objeto: " + pokemon.getObjeto() + "\n" +
                        "PS: " + pokemon.getHp() + "\n" +
                        "Ataque: " + pokemon.getAtaque() + "\n" +
                        "Defensa: " + pokemon.getDefensa() + "\n" +
                        "Velocidad: " + pokemon.getVelocidad() + "\n" +
                        "Ataque Especial: " + pokemon.getAtaqueEspecial() + "\n" +
                        "Defensa Especial: " + pokemon.getDefensaEspecial());
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

    private static void mostrarCajas(JFrame parentFrame) throws SQLException, IOException {
        JDialog cajasDialog = new JDialog(parentFrame, "Cajas", true);
        cajasDialog.setSize(500, 400);
        cajasDialog.setLayout(new GridLayout(5, 10, 5, 5));

        CajaDAO caja = new CajaDAO();
        ArrayList<Pokemon> pokemonsEnCaja = CajaDAO.listaPokemon();

        for (Pokemon pokemon : pokemonsEnCaja) {
            JButton pokemonButton = new JButton(pokemon.getNombre());
            pokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 12));
            //Cargar imagen
            String imgPokemonPath = "pokemonImagenes/" + pokemon.getNombre().toLowerCase() + ".png";
            ImageIcon imgPokemon = new ImageIcon(imgPokemonPath);
            Image scaledImage = imgPokemon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            pokemonButton.setIcon(new ImageIcon(scaledImage));
            cajasDialog.add(pokemonButton);

            pokemonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mostrarOpcionesPC(cajasDialog, pokemon.getNombre());
                    } catch (SQLException | IOException ex) {
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

        sacarButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        datosButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        cambiarApodoButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        liberarButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        salirButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));

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
                    try {
                        if (liberarPokemonDeBD(pokemonNombre)) {
                            opcionesDialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(opcionesDialog, "Error al liberar el Pokémon.");
                        }
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
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

    private static void dejarPokemon(JFrame parentFrame) throws SQLException, IOException {
        JDialog dejarDialog = new JDialog(parentFrame, "Dejar Pokémon", true);
        dejarDialog.setSize(400, 300);
        dejarDialog.setLayout(new GridLayout(6, 1, 10, 10));

        ArrayList<Pokemon> equipo = EquipoDAO.getEquipo().getEquipo();

        for (Pokemon pokemonNombre : equipo) {
            JButton pokemonButton = new JButton(String.valueOf(pokemonNombre));
            pokemonButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
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
                    dejarPokemonEnBD(pokemonNombre);
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
                    try {
                        liberarPokemonDeBD(pokemonNombre);
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
                try {
                    dejarPokemonEnBD(pokemonNombre);
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                Pokemon pokemonAnadido;
                try {
                    pokemonAnadido = CajaDAO.obtenerPokemonDeCaja(pokemonNombre);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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

        Equipo equipo = EquipoDAO.getEquipo();
        ArrayList<String> nombresPokemons = EquipoDAO.getNombresPokemons(equipo);
        JComboBox<String> pokemonComboBox = new JComboBox<>(nombresPokemons.toArray(new String[0]));

        JLabel objetoLabel = new JLabel("Selecciona el objeto:");
        objetoLabel.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        moverDialog.add(objetoLabel);

        ArrayList<String> objetos = new ArrayList<>();
        try {
            objetos = PokemonDAO.obtenerObjetosDisponiblesDesdeBD();
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(moverDialog, "Error al obtener los objetos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        JComboBox<String> objetoComboBox = new JComboBox<>(objetos.toArray(new String[0]));
        objetoComboBox.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        moverDialog.add(objetoComboBox);

        JButton moverButton = new JButton("Mover");
        moverButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        moverDialog.add(moverButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setFont(new Font("PokemonGb-RAeo", Font.PLAIN, 16));
        moverDialog.add(cancelarButton);

        moverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pokemonNombre = (String) pokemonComboBox.getSelectedItem();
                String objeto = (String) objetoComboBox.getSelectedItem();

                try {
                    Pokemon pokemon = PokemonDAO.buscarPokemonPorNombre(pokemonNombre);
                    String objetoActual = PokemonDAO.devolverObjetoPokemon(pokemon);

                    if (objetoActual != null) {
                        PokemonDAO.cambiarObjetoPokemon(pokemon, objeto);
                        JOptionPane.showMessageDialog(moverDialog, "Objeto intercambiado con éxito.");
                    } else {
                        PokemonDAO.asignarObjetoPokemon(pokemon, objeto);
                        JOptionPane.showMessageDialog(moverDialog, "Objeto asignado con éxito.");
                    }

                    EquipoDAO.guardarEquipo(equipo);
                } catch (SQLException | IOException ex) {
                    JOptionPane.showMessageDialog(moverDialog, "Error al mover el objeto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                moverDialog.dispose();
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

    private static Pokemon obtenerDatosPokemonDesdeBD(String pokemonNombre) throws SQLException, IOException {
        return PokemonDAO.buscarPokemonPorNombre(pokemonNombre);
    }

    private static String obtenerObjetoDesdeBD(Pokemon pokemon) {
        return PokemonDAO.devolverObjetoPokemon(pokemon);
    }

    private static void moverObjetoEntrePokemones(String pokemonOrigen, String pokemonDestino, String objeto) throws SQLException, IOException {
        // Aquí implementa la lógica para mover el objeto del Pokémon origen al Pokémon destino
        // Puedes utilizar tus métodos DAO correspondientes para actualizar la información en la base de datos
        // Por ejemplo:
        // 1. Obtener el Pokémon origen y eliminar el objeto de su inventario
        // 2. Obtener el Pokémon destino y agregar el objeto a su inventario
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

    private static void dejarPokemonEnBD(String pokemonNombre) throws SQLException, IOException {
        EquipoDAO.quitarPokemon(PokemonDAO.buscarPokemonPorNombre(pokemonNombre));
        CajaDAO.meterPokemonACaja(pokemonNombre);
    }

    private static void cambiarApodoEnBD(Pokemon pokemon, String nuevoNombre) throws SQLException, IOException {
        PokemonDAO.cambiarApodoPokemon(pokemon, nuevoNombre);
    }

    private static boolean liberarPokemonDeBD(String pokemonNombre) throws SQLException, IOException {
        CajaDAO.liberarPokemon(pokemonNombre);
        return true;
    }

    private static void desconectar(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Desconectando y guardando cambios...");
        System.exit(0);
    }
}
