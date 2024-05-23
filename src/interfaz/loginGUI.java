package interfaz;

import utilidades.credenciales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class LoginGUI extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contraseniaField;


    public LoginGUI() {
        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("Usuario:");
        usuarioField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        contraseniaField = new JPasswordField();

        JButton loginButton = new JButton("Iniciar Sesión");

        panel.add(userLabel);
        panel.add(usuarioField);
        panel.add(passwordLabel);
        panel.add(contraseniaField);
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usuarioField.getText();
                String password = new String(contraseniaField.getPassword());

                try {
                    credenciales.escribirCredenciales(user, password);

                    utilidades.credenciales.conectar();
                    JOptionPane.showMessageDialog(LoginGUI.this, "Conexión exitosa");

                    PCPokemonGUI pcPokemonGUI = new PCPokemonGUI(user);
                    pcPokemonGUI.setVisible(true);

                    setState(JFrame.ICONIFIED);

                    dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Error al conectar a la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI().setVisible(true);
            }
        });
    }
}
