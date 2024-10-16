#!/bin/bash

# Fájlok nevét átvarázsolja spacetelenné
for file in *.mp3; do
  new_file=$(echo "$file" | tr ' ' '-')
  
  if [ "$file" != "$new_file" ]; then
    mv "$file" "$new_file"
    echo "Renamed: '$file' -> '$new_file'"
  fi
done

