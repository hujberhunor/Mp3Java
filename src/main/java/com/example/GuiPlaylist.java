package com.example;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.MediaPlayer;

public class GuiPlaylist {

    JButton addToPlaylist, removeFromPlaylist, playButton;
    DefaultListModel<Track> playlistModel = new DefaultListModel<>(); // Changed to store Track objects
    GuiActions ga = new GuiActions();

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
        playButton = new JButton("Play");
        removeFromPlaylist = new JButton("Remove");
        buttonPanel.add(addToPlaylist);
        buttonPanel.add(playButton);
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
        playButton.addActionListener(e -> {
            JList<Track> finalizedPlaylistList = new JList<>(playlistModel);
            finalizedPlaylistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow single selection
           
            if (playlistModel.getSize() > 0) {
                JOptionPane.showMessageDialog(frame, "After pressing OK the playlist will start", "Play", JOptionPane.INFORMATION_MESSAGE);
                ga.playPlaylist(finalizedPlaylistList); // Pass the JList to the method
            } else {
                JOptionPane.showMessageDialog(frame, "Empty.", "Error", JOptionPane.WARNING_MESSAGE);
            }
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

private JFrame trackWindow;
private JLabel titleLabel;
private JLabel artistLabel;
private JLabel albumLabel;
private JButton playPauseButton;

private AudioHandler currentAudioHandler; // Központi AudioHandler

private JLabel positionLabel; // New label for track position
private Timeline positionUpdater; // Timer for updating track position

public void initializeTrackControlWindow(AudioHandler audioHandler, ArrayList<Track> trackList) {
    currentAudioHandler = audioHandler; // Set the current AudioHandler

    // If the window already exists, just make it visible
    if (trackWindow != null) {
        trackWindow.setVisible(true);
        return;
    }

    // Create new window
    trackWindow = new JFrame("Now Playing");
    trackWindow.setSize(400, 250);
    trackWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    trackWindow.setLayout(new BorderLayout());

    // Info panel
    JPanel infoPanel = new JPanel(new GridLayout(4, 1));
    titleLabel = new JLabel("Title: ", JLabel.CENTER);
    artistLabel = new JLabel("Artist: ", JLabel.CENTER);
    albumLabel = new JLabel("Album: ", JLabel.CENTER);
    positionLabel = new JLabel("Position: 0/0 s", JLabel.CENTER); // Initial position

    infoPanel.add(titleLabel);
    infoPanel.add(artistLabel);
    infoPanel.add(albumLabel);
    infoPanel.add(positionLabel);

    // Buttons panel
    JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
    playPauseButton = new JButton("Pause"); // Default state is "Pause"

    // Button actions
    playPauseButton.addActionListener(e -> {
        if (currentAudioHandler != null) {
            MediaPlayer mediaPlayer = currentAudioHandler.getMediaPlayer();
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                currentAudioHandler.pause();
                playPauseButton.setText("Play");
            } else {
                currentAudioHandler.play();
                playPauseButton.setText("Pause");
            }
        }
    });

    buttonPanel.add(playPauseButton);

    // Assemble UI
    trackWindow.add(infoPanel, BorderLayout.CENTER);
    trackWindow.add(buttonPanel, BorderLayout.SOUTH);

    // Center window and display
    trackWindow.setLocationRelativeTo(null);
    trackWindow.setVisible(true);
}

public void updateTrackControlWindow(AudioHandler audioHandler) {
    currentAudioHandler = audioHandler; // Update current AudioHandler
    if (titleLabel != null && artistLabel != null && albumLabel != null) {
        Track track = audioHandler.getTrackFromAH();
        titleLabel.setText("Title: " + track.getTitle());
        artistLabel.setText("Artist: " + track.getArtist());
        albumLabel.setText("Album: " + track.getAlbum());
    }

    // Set up the position updater
    setupPositionUpdater(audioHandler);
}

private void setupPositionUpdater(AudioHandler audioHandler) {
    if (positionUpdater != null) {
        positionUpdater.stop(); // Stop the previous updater if it exists
    }

    MediaPlayer mediaPlayer = audioHandler.getMediaPlayer();

    positionUpdater = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e -> {
    double currentTime = mediaPlayer.getCurrentTime().toSeconds();
    double totalDuration = mediaPlayer.getTotalDuration().toSeconds();
    positionLabel.setText(String.format("Position: %.0f/%.0f s", currentTime, totalDuration));
}));

    positionUpdater.setCycleCount(Animation.INDEFINITE);
    positionUpdater.play();
}



}
