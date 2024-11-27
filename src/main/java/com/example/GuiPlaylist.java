package com.example;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class GuiPlaylist {

    JButton addToPlaylist, removeFromPlaylist, finalizePlaylist;
    DefaultListModel<Track> playlistModel = new DefaultListModel<>(); // Changed to store Track objects

    /**
     * Pop-up window for search results.
     * Called by the searchTrack() method in GuiHandler.
     * 
     * @param results The list of tracks found by the search.
     */
    public void showSearchResultsWindow(ArrayList<Track> results) {
        JFrame frame = new JFrame("Playlist Maker");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Song List (Search Results)
        JPanel songsPanel = new JPanel(new BorderLayout());
        DefaultListModel<Track> resultsModel = new DefaultListModel<>();
        results.forEach(resultsModel::addElement); // Add Track objects to the model
        JList<Track> resultsList = new JList<>(resultsModel);
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane songsScrollPane = new JScrollPane(resultsList);
        songsPanel.add(songsScrollPane, BorderLayout.CENTER);
        songsPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));

        // Playlist
        JPanel playlistPanel = new JPanel(new BorderLayout());
        JList<Track> playlistList = new JList<>(playlistModel);
        JScrollPane playlistScrollPane = new JScrollPane(playlistList);
        playlistPanel.add(playlistScrollPane, BorderLayout.CENTER);
        playlistPanel.setBorder(BorderFactory.createTitledBorder("Playlist"));

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        addToPlaylist = new JButton("Add");
        finalizePlaylist = new JButton("Finalize playlist");
        removeFromPlaylist = new JButton("Remove");
        buttonPanel.add(addToPlaylist);
        buttonPanel.add(finalizePlaylist);
        buttonPanel.add(removeFromPlaylist);

        // Add ActionListener for "Add" button
        addToPlaylist.addActionListener(e -> {
            Track selectedTrack = resultsList.getSelectedValue();
            if (selectedTrack != null && !playlistModel.contains(selectedTrack)) {
                playlistModel.addElement(selectedTrack);
            } else if (selectedTrack == null) {
                JOptionPane.showMessageDialog(frame, "No song selected.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Song already in playlist.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
       
        // Add ActionListener for "Finalize playlist" button
        finalizePlaylist.addActionListener(e -> {
            // Close the search results window
            frame.dispose(); // Close the previous window (search results window)
            
            // Call finalizePlaylistPanel() method and pass the current playlist model
            finalizePlaylistPanel(playlistModel);
        });


        // Add ActionListener for "Remove" button
        removeFromPlaylist.addActionListener(e -> {
            Track selectedTrack = playlistList.getSelectedValue();
            if (selectedTrack != null) {
                playlistModel.removeElement(selectedTrack);
            } else {
                JOptionPane.showMessageDialog(frame, "No song selected to remove.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add panels to frame
        frame.add(songsPanel, BorderLayout.NORTH);
        frame.add(playlistPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    } // end of showSearchResultsWindow()

    public void finalizePlaylistPanel(DefaultListModel<Track> playlListModel){
    JFrame frame = new JFrame("Finalized Playlist");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(400, 300);
    frame.setLayout(new BorderLayout());

    // Playlist Panel (similar to the one in showSearchResultsWindow)
    JPanel playlistPanel = new JPanel(new BorderLayout());
    JList<Track> finalizedPlaylistList = new JList<>(playlListModel);
    finalizedPlaylistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow single selection
    JScrollPane playlistScrollPane = new JScrollPane(finalizedPlaylistList);
    playlistPanel.add(playlistScrollPane, BorderLayout.CENTER);
    playlistPanel.setBorder(BorderFactory.createTitledBorder("Finalized Playlist"));

    // Play Button Panel
    JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
    JButton playButton = new JButton("Play");
    buttonPanel.add(playButton);

    // Add ActionListener for Play button (example action)
    playButton.addActionListener(e -> {
        // Get the selected index from the playlist
        int selectedIndex = finalizedPlaylistList.getSelectedIndex();

        if (selectedIndex != -1) { // If a track is selected
            Track selectedTrack = playlListModel.get(selectedIndex);  // Get the selected track from the model
            JOptionPane.showMessageDialog(frame, "Playing: "+ selectedIndex + " asd "  + selectedTrack.toString(), "Play", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "No song selected.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    });

    // Add panels to frame
    frame.add(playlistPanel, BorderLayout.CENTER);
    frame.add(buttonPanel, BorderLayout.SOUTH);

    frame.setVisible(true);
}


}
