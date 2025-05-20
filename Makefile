# Direktori sumber dan keluaran
SRC_DIR=src
BIN_DIR=bin
MAIN_CLASS=Main

# Daftar semua file sumber Java
SOURCES=$(wildcard $(SRC_DIR)/*.java)

# Aturan default
all: compile run

# Compile semua source ke dalam bin/
compile:
	javac -d $(BIN_DIR) $(SOURCES)

# Jalankan dengan file test default
run:
	java -cp $(BIN_DIR) $(MAIN_CLASS) test/a.txt

# Hapus semua hasil kompilasi
clean:
	rm -rf $(BIN_DIR)/*
