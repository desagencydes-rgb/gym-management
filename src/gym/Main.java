package gym;

import gym.service.GymService;
import gym.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static void main(String[] args) {
        // Use File-based repository by default.
        // Ensure data directory exists
        new java.io.File("data").mkdirs();

        SwingUtilities.invokeLater(() -> {
            try {
                // Attempt to set FlatLaf Dark
                // We use reflection or try-catch string lookup to avoid compile errors if jar
                // missing
                try {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException e) {
                    // Fallback to system or nimbus if desired, but System is usually safe
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }

                // Customize Global UI properties (optional, mainly if FlatLaf is active)
                UIManager.put("Button.arc", 10);
                UIManager.put("Component.arc", 10);
                UIManager.put("ProgressBar.arc", 10);
                UIManager.put("TextComponent.arc", 10);

                // Ensure key global colors match our Style if FlatLaf isn't exact
                // (Note: FlatLaf usually handles this well, but we can force defaults)

            } catch (Exception e) {
                e.printStackTrace();
            }

            GymService service = new GymService();
            MainFrame frame = new MainFrame(service);
            frame.setVisible(true);
        });
    }
}
