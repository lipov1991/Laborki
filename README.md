
# Cel laboratoriów

Celem laboratoriów jest zapoznanie studentów z nowoczesnymi technikami tworzenia aplikacji na Androida. Na każdych zajęciach rozwijana jest aplikacja o nazwie "Laborki". 


# Sposób realizacji projektu

Projekt jest realizowany w grupach dwuosobowych. Na początku każda z grup tworzy brancz (z developa) i nazywa go według schemtu: nazwisko1_nazwisko2.
Każde zadanie powinno zostać zakończone commitem z wiadomością według schematu: lab_nr (np. lab_1). Po pierwszym commicie (tylko) należy wykonać pull requesta do developa. W celu zaliczenia zadania należy w określonym terminie wykonać commit.


# Laboratorium 1

Tematy: 

- Przygotowanie środowiska programistycznego
- Architektura MVVM
- Wstrzykiwanie zależności
- Zapoznanie się z szablonem aplikacji "Laborki"
- Obsługa gestów
- Obsługa sensorów

<b>Zadanie 1 (2p).</b> Obsłużyć następujące zdarzenia na głównym ekranie aplikacji: przytrzymanie palca na ekranie, podwójne kliknięcie w ekran oraz zmiana przyśpieszenia (akceleracji). Po wykryciu zdarzenia, na ekranie powinien pojawić się odpowiedni tekst (LONG_PRESS, DOUBLE_TAP, ACCELERATION_CHANGE). Do obsługi kliknięć należy użyć klasy GestureDetector.SimpleOnGestureListener. W celu wykrycia zmiany przyśpieszenia należy zaimplementować interefs SensorEventListener. W zadaniu należy skorzystać z klas pomocniczych: GestureDetectorUtils oraz SensorEventsUtils. Zdarzenie ACCELERATION_CHANGE powinno być emitowane tylko wtedy, gdy  parametry x i y przyjmują wartości większe od 5m/s^2. W przypadku gdy nie wykryto ackelerometra (accelerometer == null), na głównym ekranie powinien wyświetlić się tekst "Nie wykryto akcelerometra.".

Materiały pomocnicze: 

- https://github.com/lipov1991/PMAG/tree/master/Materia%C5%82y%20pomocnicze/Laboratorium%201.pdf
- https://developer.android.com/training/gestures/detector
- https://developer.android.com/guide/topics/sensors/sensors_motion

#### Termin realizacji zadania: 04.03.2020 godz. 21.00


# Laboratorium 2

Tematy: 

- Tworzenie widoków
- Animacje
- Kolekcje

<b>Zadanie 2 (3p).</b> Dodać mechanizm uwierzytelniania użytkownika na podstawie gestów i zmiany akceleracji. Prawidłowa sekwencja zdarzeń to: DOUBLE_TAP, DOUBLE_TAP, LONG_PRESS, ACCELERATION_CHANGE. Po 3 nieudanych próbach należy zablokować możliwość logowania. W ocenie zadania będzie również uwzględniony wkład artystyczny. Interfejs powinien być instuicyjny.

#### Termin realizacji zadania: 11.03.2020 godz. 21.00


# Laboratorium 3

Tematy: 

- Obsługa usług zewnętrznych z wykorzystaniem biblioteki Retrofit
- Programowanie reaktywne z wykorzystaniem biblioteki RxJava

#### Termin realizacji zadania: 18.03.2020 godz. 21.00


# Laboratorium 4

Temat: Integracja z Google Maps API

#### Termin realizacji zadania: 25.03.2020 godz. 21.00


# Laboratorium 5

Temat: Interakcja z mapą

#### Termin realizacji zadania: 01.04.2020 godz. 21.00

CDN...
-

# Ocena końcowa

W ocenie końcowej uwględniane są: działanie aplikacji oraz styl kodu. W przypadku oddania zadania po terminie odejmowane są 2 punkty.

Skala ocen:

100%-91% - 5<br/>
90%-86%  - 4.5<br/>
85%-71%  - 4<br/>
70%-61%  - 3.5<br/>
60%-51%  - 3<br/>
 <50%    - 2
