#!/bin/bash

# ===================================================================
# FinTrack Local Database Reset Script (v3 - Named Volume)
# ===================================================================
# This script uses named volumes for maximum reliability. It tears
# down the container and deletes the volume it uses, ensuring a
# completely fresh start every time.
#
# USAGE:
# 1. Make it executable: chmod +x reset-db.sh
# 2. Run it from the project root: ./reset-db.sh
# ===================================================================

# Exit immediately if a command exits with a non-zero status.
set -e

# Use color codes for better readability
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}Stopping containers and removing the database volume...${NC}"
# The --volumes flag is crucial here. It tells docker-compose to remove
# the named volume 'postgres-data' along with the container.
docker-compose down --volumes

echo -e "${YELLOW}Starting a fresh PostgreSQL container in the background...${NC}"
docker-compose up -d

echo -e "${GREEN}✅ Local database has been successfully reset!${NC}"

