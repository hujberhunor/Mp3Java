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






     /**
     * Felugró ablak a search resultok miatt
     * GuiHandlerben a searchTrack() method hívja meg a kííratást
     * @param results The list of tracks found by the search.
     */
    public void showSearchResultsWindow(ArrayList<Track> results) {
        JFrame frame = new JFrame("Playlist Maker");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Song List (Search Results)
        JPanel songsPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> resultsModel = new DefaultListModel<>();
        results.stream()
                .map(track -> track.getTitle() + " - " + track.getArtist())
                .forEach(resultsModel::addElement);
        JList<String> resultsList = new JList<>(resultsModel);
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane songsScrollPane = new JScrollPane(resultsList);
        songsPanel.add(songsScrollPane, BorderLayout.CENTER);
        songsPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));

        // Playlist
        JPanel playlistPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> playlistModel = new DefaultListModel<>();
        JList<String> playlistList = new JList<>(playlistModel);
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
            String selectedSong = resultsList.getSelectedValue();
            if (selectedSong != null && !playlistModel.contains(selectedSong)) {
                playlistModel.addElement(selectedSong);
            } else if (selectedSong == null) {
                JOptionPane.showMessageDialog(frame, "No song selected.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Song already in playlist.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add ActionListener for "Remove" button
        removeFromPlaylist.addActionListener(e -> {
            String selectedSong = playlistList.getSelectedValue();
            if (selectedSong != null) {
                playlistModel.removeElement(selectedSong);
            } else {
                JOptionPane.showMessageDialog(frame, "No song selected to remove.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add panels to frame
        frame.add(songsPanel, BorderLayout.NORTH);
        frame.add(playlistPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }   




}
