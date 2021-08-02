package view;

import controller.AppController;
import model.Assignment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class HomeScreen {

    private final JFrame homeScreen;
    private JPanel homeScreenPanel;
    private JButton startNewButton;
    private JLabel titleLabel;
    private JTextPane descriptionLabel;
    private JButton loadButton;
    private JButton helpButton;

    private AppController controller;

    public HomeScreen(AppController controller) {
        this.controller = controller;

        // Create the home screen jFrame
        homeScreen = new JFrame("Feedback Helper Tool");
        homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeScreen.setSize(800, 600);

        // Setup the components and display the screen
        createHomeScreenComponents();
        displayHomeScreen();
    }

    public void createHomeScreenComponents() {
        createHomeScreenPanel();
        createTitle();
        createDescription();
        createButtons();
    }

    public void displayHomeScreen() {
        homeScreenPanel.add(titleLabel);
        homeScreenPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        homeScreenPanel.add(descriptionLabel);
        homeScreenPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        homeScreenPanel.add(startNewButton);
        homeScreenPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        homeScreenPanel.add(loadButton);
        homeScreenPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        //homeScreenPanel.add(helpButton);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - homeScreen.getWidth())/2;
        int y = (screenSize.height - homeScreen.getHeight())/2;
        homeScreen.setLocation(x, y);

        // Add home screen panel to home screen frame
        homeScreen.add(homeScreenPanel);
        homeScreen.setVisible(true);
    }


    public void createHomeScreenPanel() {
        homeScreenPanel = new JPanel();

        // Layout from top to bottom
        homeScreenPanel.setLayout(new BoxLayout(homeScreenPanel, BoxLayout.PAGE_AXIS));
        homeScreenPanel.setBorder(BorderCreator.createEmptyBorder(50));
    }

    public void createTitle() {
        titleLabel = new JLabel();
        titleLabel.setText("Feedback Helper Tool");
        titleLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0,0,20,0));//top,left,bottom,right
    }

    public void createDescription() {
        descriptionLabel = new JTextPane();
        descriptionLabel.setText(
                "Welcome to the Feedback Helper Tool! " +
//                "To learn how to use this tool, please click the 'Help' button at the bottom of the screen. " +
                "To get started with creating feedback documents click the 'Start New Assignment' button. " +
                "You will then be prompted to setup your assignment via a JSON configuration file or through a manual guided setup. " +
                "To resume creating feedback documents click the 'Load Assignment' button and select your '.fht' file."
        );

        descriptionLabel.setBorder(BorderCreator.createAllSidesEmptyBorder(20));
        descriptionLabel.setMaximumSize(new Dimension(500, 210));
        descriptionLabel.setPreferredSize(new Dimension(500, 210));
        descriptionLabel.setMinimumSize(new Dimension(500, 210));
        descriptionLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        descriptionLabel.setEditable(false);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void createButtons() {
        startNewButton = new JButton("Start New Assignment");
        startNewButton.setMaximumSize(new Dimension(300, 50));
        startNewButton.setPreferredSize(new Dimension(300, 50));
        startNewButton.setMinimumSize(new Dimension(300, 50));
        startNewButton.addActionListener(e -> {
            homeScreen.dispose();
//            CreateAssignmentScreen createAssignmentScreen = new CreateAssignmentScreen(controller);
            SetupOptionsScreen setupOptionsScreen = new SetupOptionsScreen(controller);
        });
        startNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadButton = new JButton("Resume Assignment");
        loadButton.setMaximumSize(new Dimension(300, 50));
        loadButton.setPreferredSize(new Dimension(300, 50));
        loadButton.setMinimumSize(new Dimension(300, 50));
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose an assignment to resume");

            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Assignment Files", "fht");
            fileChooser.addChoosableFileFilter(filter);

            int returnValue = fileChooser.showDialog(this.homeScreen, "Resume this assignment");
            String assignmentFilePath = null;
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                assignmentFilePath = fileChooser.getSelectedFile().getPath();
                System.out.println("Assignment file path: " + assignmentFilePath);
                homeScreen.dispose();
            }

            if (assignmentFilePath != null) {
                new Thread(SetupOptionsScreen::showLoadingScreen).start();
                Assignment assignment = controller.loadAssignment(assignmentFilePath);
                FeedbackScreen feedbackScreen = new FeedbackScreen(controller, assignment);
            }
        });

//        helpButton = new JButton("Help");
//        helpButton.setMaximumSize(new Dimension(300, 50));
//        helpButton.setPreferredSize(new Dimension(300, 50));
//        helpButton.setMinimumSize(new Dimension(300, 50));
//        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

}
