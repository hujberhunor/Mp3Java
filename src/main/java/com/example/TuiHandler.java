package com.example;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class TuiHandler {
    GuiActions actions = new GuiActions();
    Track track;

   // KOmponensek
    JFrame frame;
    JTextField artistField;
    JTextField titleField;
    JLabel statusLabel;
    JButton playButton;
    JButton fileSel;

    public void init(){
        initFrame();
        initTextFields();
    }
  

    public void initFrame() {
        frame = new JFrame("Mp3Java"); // Set the title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        frame.setSize(600, 400); // Set the size of the frame
        frame.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;  // Make the components expand horizontally

        // Row 1
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 1
        frame.add(new JLabel("Artist:"), gbc);
        artistField = new JTextField(20); // Create the JTextField
        artistField.setEditable(false); // Make it non-editable
        gbc.gridx = 1; // Column 1
        gbc.gridwidth = 2; // Span 2 columns
        frame.add(artistField, gbc);
        gbc.gridwidth = 1; // Reset gridwidth for next components

        // Row 2
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        frame.add(new JLabel("Title:"), gbc);
        titleField = new JTextField(20); // Create the JTextField
        titleField.setEditable(false); // Make it non-editable
        gbc.gridx = 1; // Column 1
        gbc.gridwidth = 2; // Span 2 columns
        frame.add(titleField, gbc);
        gbc.gridwidth = 1; // Reset gridwidth for next components


        // Row 3
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 1
        frame.add(new JLabel("Progress:"), gbc);
        gbc.gridx = 1; // Column 1
        gbc.gridwidth = 2; // Span 2 columns
        gbc.gridwidth = 1; // Reset gridwidth for next components


        // Row 4
        gbc.gridx = 0; // Column 0
        gbc.gridy = 4; // Row 3
        fileSel = new JButton("File select");
        frame.add(fileSel, gbc);
        gbc.gridx = 1; // Column 1
        playButton = new JButton("Play");
        frame.add(playButton, gbc);

        gbc.gridx = 2; // Column 2

        // Adjust frame settings
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        // Action listeners
        playButton.addActionListener(e -> actions.playPressed());
        
        fileSel.addActionListener(e ->{

            try {
                // sets the selected track
                track = actions.selectTrack(frame);
                // Refresh the textField to the selected track
                initTextFields();
            } 
            catch (UnsupportedTagException | InvalidDataException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } 
        });
    }

    public void initTextFields(){
        // Actions 
        // Init text fields
        // TODO refresh text fields
        actions.fillArtist(artistField);
        actions.fillTitle(titleField);
    }

 }
