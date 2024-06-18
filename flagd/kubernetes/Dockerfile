FROM ghcr.io/open-feature/flagd:latest

# Set the working directory
WORKDIR /app

# Copy the configuration file from the host machine to the container
COPY demo.flagd.json /app/demo.flagd.json

# Define the command to run the flagd server
CMD ["start", "--uri", "file:/app/demo.flagd.json"]