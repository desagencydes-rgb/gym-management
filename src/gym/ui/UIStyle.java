package gym.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIStyle {

    // Colors
    public static final Color BG_COLOR = new Color(24, 24, 24); // Deep dark background
    public static final Color CARD_COLOR = new Color(35, 35, 35); // Slightly lighter for cards
    public static final Color ACCENT_COLOR = new Color(0, 153, 255); // Electric Blue
    public static final Color TEXT_PRIMARY = new Color(240, 240, 240); // White-ish
    public static final Color TEXT_SECONDARY = new Color(170, 170, 170); // Gray
    public static final Color DANGER_COLOR = new Color(220, 53, 69); // Red
    public static final Color SUCCESS_COLOR = new Color(40, 167, 69); // Green

    // Fonts
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBHEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    // Padding/Dimension Constants
    public static final int PADDING_HUGE = 30;
    public static final int PADDING_LARGE = 20;
    public static final int PADDING_MEDIUM = 10;
    public static final int PADDING_SMALL = 5;

    /**
     * Creates a styled header label.
     */
    public static JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADER_FONT);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    /**
     * Creates a styled sub-header label.
     */
    public static JLabel createSubHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUBHEADER_FONT);
        label.setForeground(TEXT_SECONDARY);
        return label;
    }

    /**
     * Creates a standard primary button.
     */
    public static JButton createPrimaryButton(String text) {
        return createStyledButton(text, ACCENT_COLOR, Color.WHITE);
    }

    /**
     * Creates a secondary/generic button.
     */
    public static JButton createSecondaryButton(String text) {
        return createStyledButton(text, CARD_COLOR, TEXT_PRIMARY);
    }

    /**
     * Creates a destructive button (Delete/Cancel).
     */
    public static JButton createDestructiveButton(String text) {
        return createStyledButton(text, DANGER_COLOR, Color.WHITE);
    }

    private static JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON_FONT);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);

        // Fix for Windows L&F ignoring background color
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20)); // Add manual padding since border is gone

        // Add simple hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.brighter());
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
                btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.brighter());
            }
        });

        return btn;
    }

    /**
     * Wraps a component in a styled "Card" panel.
     */
    public static JPanel createCard(JComponent content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(new EmptyBorder(PADDING_LARGE, PADDING_LARGE, PADDING_LARGE, PADDING_LARGE));
        if (content != null) {
            card.add(content, BorderLayout.CENTER);
        }
        return card;
    }
}
