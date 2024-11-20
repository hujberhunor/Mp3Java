package com.example;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;

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
    JFrame frame;
    JTextField searchField, artistField, titleField, albumField, progressField;
    JButton playButton, fileSelectButton;
    Timer progressTimer;

    public void init() {
        initFrame();
        initComponents();
        configureProgressTimer();
    }

    private void initFrame() {
        frame = new JFrame("Mp3Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
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
        gbc.weightx = 1.0;

        // Top row: Search field
        addSearchLabelAndField(gbc, 0, "Search:", searchField = new JTextField(20));

        // Artist row
        addLabelAndField(gbc, 1, "Artist:", artistField = new JTextField(20));

        // Title row
        addLabelAndField(gbc, 2, "Title:", titleField = new JTextField(20));

        // Album row
        addLabelAndField(gbc, 3, "Album:", albumField = new JTextField(20));

        // Middle row: Progress field
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        progressField = createReadOnlyField();
        frame.add(progressField, gbc);

        // Bottom row: File Select and Play/Stop buttons
        gbc.gridwidth = 1;
        gbc.gridy = 5;
        fileSelectButton = new JButton("File Select");
        gbc.gridx = 0;
        frame.add(fileSelectButton, gbc);

        playButton = new JButton("Play");
        gbc.gridx = 1;
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

    /**
     * A progressField frissítése miatt van rá szükség, emiatt lesz "reszponzív"
     */
    private void configureProgressTimer() {
        progressTimer = new Timer(1000, e -> actions.fillProgress(progressField));
    }

    private void selectTrack() {
        try {
            track = actions.selectTrack(frame);
            initComponents();
        } catch (UnsupportedTagException | InvalidDataException | IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Toggle mechanizmus
     * Számon tartja hogy meg volt-e nyomva a gomb 
     * és aszerint alakítja a play/pause gomb feliratát
     */
    private void togglePlay() {
        actions.playPressed();
        updateDynamicFields();
        
        String currentStatus = actions.fillStatus();
        playButton.setText(currentStatus.equals("PLAYING") ? "Pause" : "Play"); /// Ez a sor kerül be a play gomb-ra

        if (actions.pressed) {
            progressTimer.start();
        } else {
            progressTimer.stop();
        }
    }

    /// A dinamikusan frissülő (progress bar) fieldek
    private void updateDynamicFields() {
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
