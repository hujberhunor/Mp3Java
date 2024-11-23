package com.example;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

// A felugró ablakhoz kell
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;



public class GuiHandler {
    GuiActions actions = new GuiActions();
    Track track;

   // KOmponensek
    JFrame frame;
    JTextField artistField;
    JTextField titleField;
    JTextField statusField;
    JTextField progressField;
    
    JLabel statusLabel;
   
    JButton playButton;
    JButton fileSel;

    Timer progressTimer;

    public void init(){
        initFrame();
        initStaticTextFields();
        progressTimer = new Timer(1000, e -> actions.fillProgress(progressField));

    }
  

    public void initFrame() {
        frame = new JFrame("Mp3Java"); // Set the title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        frame.setSize(600, 400); // Set the size of the frame
        frame.setLayout(new GridBagLayout());
        setupLayout();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Initeli a layoutot
     */
    private void setupLayout() {
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
        gbc.gridy = 2; // Set to Row 2

        // Add statusField in Column 1
        gbc.gridx = 1; // Column 1
        statusField = new JTextField();
        statusField.setEditable(false);
        frame.add(statusField, gbc);

        // Add progressField in Column 2
        gbc.gridx = 2; // Column 2
        progressField = new JTextField();
        progressField.setEditable(false);
        frame.add(progressField, gbc);

        // Add blank space in Column 3
        gbc.gridx = 3; // Column 3
        frame.add(new JLabel(""), gbc);

        // Reset gridwidth for the next components if needed
        gbc.gridwidth = 1;

        // Row 4
        gbc.gridx = 0; // Column 0
        gbc.gridy = 4; // Row 3
        fileSel = new JButton("File select");
        frame.add(fileSel, gbc);
        gbc.gridx = 1; // Column 1
        playButton = new JButton("Play");
        frame.add(playButton, gbc);

        addButtonListeners();
    }

    /**
     * Külön a search fildnek egy sor ami editelhető
     * @param gbc grid bag constrain layout
     * @param row sort specifikál
     * @param label a nevét adja meg a sor labeljének
     * @param field a textfield amibe kerül majd az adat
     *  */
    private void addSearchLabelAndField(GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        frame.add(new JLabel(label), gbc);
        field.setEditable(true); // Make searchField editable
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frame.add(field, gbc);
        gbc.gridwidth = 1;
    }

    /**
     * Általános sor leírása
     * Searchhot képest csak az editable(faslse) a változás
     * @param gbc grid bag constrain layout
     * @param row sort specifikál
     * @param label a nevét adja meg a sor labeljének
     * @param field a textfield amibe kerül majd az adat
     *  */
    private void addLabelAndField(GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        frame.add(new JLabel(label), gbc);
        field.setEditable(false);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        frame.add(field, gbc);
        gbc.gridwidth = 1;
    }

    private JTextField createReadOnlyField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        return field;
    }

    /**
     * Inicializálja "statikus" komponenseit a gui-nak
     */
    private void initComponents() {
        actions.fillArtist(artistField);
        actions.fillTitle(titleField);
        actions.fillAlbum(albumField); // Fill the album field if the method is available in GuiActions
    }

    /**
     * Gombok listenerjei
     */
    private void addButtonListeners() {
        fileSelectButton.addActionListener(e -> selectTrack());
        playButton.addActionListener(e -> togglePlay());

        // Add ActionListener to the search field
        searchField.addActionListener(e -> {
            try {
                searchTrack(searchField.getText());
            } catch (UnsupportedTagException | InvalidDataException | IOException e1) {
                e1.printStackTrace();
            } 
        });
    }

    public void initStaticTextFields(){
        actions.fillArtist(artistField);
        actions.fillTitle(titleField);

    }

    public void initDinTextFields(){
        actions.fillStatus(statusField); // need uptade at every play button press
        actions.fillProgress(progressField);
    }


     /**
     * Felugró ablak a search resultok miatt
     * @param results The list of tracks found by the search.
     */
    private void showSearchResultsWindow(ArrayList<Track> results) {
        JFrame resultsFrame = new JFrame("Search Results");
        resultsFrame.setSize(400, 300);
        resultsFrame.setLayout(new GridBagLayout());
        resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        //  lista a resultoknak
        JList<String> resultsList = new JList<>(
                results.stream().map(track -> track.getTitle() + " - " + track.getArtist()).toArray(String[]::new));
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(resultsList);
    
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        resultsFrame.add(scrollPane, gbc);

        JButton selectButton = new JButton("Select");
        gbc.gridy = 1;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        resultsFrame.add(selectButton, gbc);

        // Add functionality to select a track and close the results window
        selectButton.addActionListener(e -> {
            int selectedIndex = resultsList.getSelectedIndex();
            if (selectedIndex >= 0) {
                track = results.get(selectedIndex);
                actions.selectedFromSearch(track);
                initComponents();
                resultsFrame.dispose();
            }
        });
         
        resultsFrame.setVisible(true);
    }

    /**
     * Csak továbbadja a paramétereit az azonos paraméterezésű searchTrack fv-nek ami
     * a fileHandlerben van definiálva.  
     * @param pattern Amit keresek
     * @throws UnsupportedTagException
     * @throws InvalidDataException
     * @throws IOException
     */
    private void searchTrack(String pattern) throws UnsupportedTagException, InvalidDataException, IOException {
        System.out.println("Searching for: " + pattern);
        ArrayList<Track> tracks = actions.searchTrack(pattern);
    
        if (tracks.isEmpty()) {
            System.out.println("No results found.");
        } else {
            showSearchResultsWindow(tracks);
        }
    }

} // end of GuiHandler
