#!/bin/bash

# Warm up the app.  For now, just call curl, we may want a way for this to be configurable
for i in {1..10000}; do curl http://localhost:8080; done
echo ""