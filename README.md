# Penyelesaian Puzzle Rush Hour Menggunakan Algoritma Pathfinding
> Tugas Kecil 3 IF 2211 Strategi Algoritma

> Disusun oleh Muhammad Ghifary Komara Putra - 13523066 dan Sabilul Huda - 13523072

![image](https://github.com/user-attachments/assets/80bd3685-ad4a-4e02-b05a-7fad84a736f8)


### Deskripsi Singkat
Selamat datang di program Penyelesaian Puzzle Rush Hour Menggunakan Algoritma Pathfinding! Melalui program yang dikembangkan dengan bahasa Java ini, Anda dapat memasukkan file *.txt mengenai konfigurasi papan permainan puzzle rush hour, dan program akan mengembalikan langkah-langkah penyelesaian puzzle tersebut. Program dapat melakukan pencarian dengan 3 alternatif algoritma, yaitu UCS, Greedy Best First Search, dan A\*. Selamat mencoba!

### Requirement, Instalasi, dan Panduan Penggunaan
1. Program dikembangkan dengan Java versi 11.0.27. Silakan lakukan instalasi versi tersebut atau versi Java yang kompatibel
2. Untuk mempermudah kompilasi, pastikan pula perangkat Anda memiliki ```Makefile```
3. Clone repositori ini ke dalam perangkat Anda
4. Pada CLI, pastikan anda berada pada root directory
5. Jalankan perintah berikut: ```make all```
6. Program siap dijalankan!

### Catatan
Program menerima masukan file *.txt dengan format berikut:
```
R C
N
konfigurasi_papan
```
dengan R dan C berturut-turut merupakan dimensi baris dan kolom papan, N merupakan jumlah piece yang bukan primary piece, dan konfigurasi_papan sebagai matriks disertai petak exit.
Contoh:
```
6 6
11
AAB..F
..BCDF
GPPCDFK
GH.III
GHJ...
LLJMM.
```
