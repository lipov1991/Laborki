
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

#### Termin realizacji zadania: 18.03.2021 godz. 21.00


# Laboratorium 2

Tematy: 

- Aktywności
- Animacje
- Kolekcje

<b>Zadanie 2 (3p).</b> Dodać mechanizm uwierzytelniania użytkownika na podstawie gestów i zmiany akceleracji. Prawidłowa sekwencja zdarzeń to: DOUBLE_TAP, DOUBLE_TAP, LONG_PRESS, ACCELERATION_CHANGE. Po 3 nieudanych próbach logowania należy zablokować interfejs. 
W ocenie zadania będzie również uwzględniony wkład artystyczny. Interfejs powinien być instuicyjny.

Materiały pomocnicze:

- https://github.com/lipov1991/PMAG/tree/develop/Materia%C5%82y%20pomocnicze/Laboratorium%202

#### Termin realizacji zadania: 25.03.2021 godz. 21.00


# Laboratorium 3

Tematy: 

- Tworzenie warstw w widokach
- Tworzenie nowoczesnych kontrolek
- Fragmenty
- Komunikacja z bazą danych Firebase

<b>Zadanie 3 (3p).</b> Dodać splash screen oraz 2 ekrany logowania. Na pierwszym z nich proszę umieścić kontrolkę EditText do loginu. Drugi ekran ma prezentować komponent wykonany w ramach poprzednich laboratoriów. Loginy oraz klucze (eventy) są przechowywane w bazie danych Firebase.

Instrukcja do zadania wraz z makietami: 

https://github.com/lipov1991/PMAG/tree/develop/Materia%C5%82y%20pomocnicze/Labolatorium%203

Materiały pomocnicze:

- https://firebase.google.com/docs/database/android/start

#### Termin realizacji zadania: 01.04.2021 godz. 21.00


# Laboratorium 4

Tematy: 

- Integracja z Google Maps API
- Komunikacja fragmentu z aktywnością

<b>Zadanie 4 (3p).</b> Po zalogowaniu wyświetlić mapę. Po załadowaniu mapy płynnie wycentrować kamerę na Galerii Wileńskiej (latitude: 52.2550; longitude: 21.0378). Poziom przybliżenia ustawić na 18. Galeria ma być oznaczona markerem z tytułem "Galeria Wileńska". U dołu ekranu mają się wyswietlać się 3 przyciski (FloatingActionButton) umożliwiające umieszczanie w galerii lokali 3 różnych kategorii: market, restauracja, bank. Po kliknięciu w przycisk ma nastąpić zmiana aktywnej kategorii lokalu. Ustawienie lokalu w wybranej lokalizacji ma być możliwe po przytrzymaniu palca w wybranym punkcie. Po kliknięciu (krótkim) w lokal nad jego markerem ma się wyświetlić tytuł z nazwą kategorii. Po długim kliknięciu w lokal ma się włączyć tryb drag and drop. Ograniczyć liczbę lokali z każdej kategorii do jednego. Umożliwić usuwanie lokalu z mapy. 

Materiały pomocnicze:

- https://developers.google.com/maps/documentation/android-sdk/start
- https://developers.google.com/maps/documentation/android-sdk/views#updating_the_camera_view
- https://developers.google.com/maps/documentation/android-sdk/views#zoom

#### Termin realizacji zadania: 08.04.2021 godz. 23.59


# Laboratorium 5

Temat: Indoor Maps

<b>Zadanie 5 (2p).</b> Obsłużyć opcję zmiany piętra: lokal umieszczony na n-tym piętrze ma być widoczny tylko na tym piętrze. Zablokować możliwość umieszczania lokalu w momencie gdy przyciski do zmiany piętra są niewidoczne.

Materiały pomocnicze:

- https://developers.google.com/maps/documentation/android-sdk/map#indoor_maps
- https://developers.google.com/maps/documentation/android-sdk/reference/com/google/android/libraries/maps/GoogleMap#setOnIndoorStateChangeListener(com.google.android.libraries.maps.GoogleMap.OnIndoorStateChangeListener)

#### Termin realizacji zadania: 15.04.2021 godz. 23.59


# Laboratorium 6

Tematy: 

- BottomSheetDialogFragment
- RecyclerView

<b>Zadanie 6 (3p).</b> 

Dodać przycisk (w lewym dolnym rogu) umożliwiający wybór bydynku. Po kliknięciu w przycisk ma się wyświetlić komponent bottom sheet 
z listą dostępnych budynków. Po wybraniu budynku:
- płynnie wycentrować kamerę w okreśonej lokalizacji,
- usunąć markery: Market, Fast food, Bank (jeśli znajdują się na mapie),
- zresetować statusy przycisków: Market, Fast food, Bank (domyślnie aktywnym przyciskiem powinien być Market).

Informacje o budynkach należy pobrać z https://laborki-7e3b1.firebaseio.com/galeries.json.

#### Termin realizacji zadania: 22.04.2021 godz. 23.59


# Laboratorium 7

Tematy:

- Tworzenie dialogów zgodnie ze specyfiką Material Design
- Heatmap

<b>Zadanie 7 (2p).</b> Dodać przycisk (w prawym górnym rogu) do wysyłania na serwer planu zagospodarowania budynku. Na ten moment obsługa przycisku powinna wyglądać w uproszczony sposób: 

1. Wyświetlenie toasta z tekstem "Plan zagospodarowania został wysłany na serwer.".
2. Usunięcie markerów: Market, Fast food, Bank (jeśli znajdują się na mapie).
3. Zresetowanie statusów przycisków: Market, Fast food, Bank (domyślnie aktywnym przyciskiem powinien być Market).

W przypadku zmiany galerii bez uprzedniego kliknięcia w przycisk do uploadowania, powinien wyświetlić się dialog:

- tytuł: nazwa aktywnej galerii (którą użytkownik zamierza "opóścić")
- treść: "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?"
- przycisk NIE: zamknięcie dialogu
- przycisk TAK: usunięcie markerów: Market, Fast food, Bank (jeśli znajdują się na mapie) + zresetowanie statusów przycisków: 
  Market, Fast food, Bank (domyślnie aktywnym przyciskiem powinien być Market) + zamknięcie dialogu.
  
Dialog powinien się również wyświetlić po kliknięcia Wstecz (na ekranie z mapą) bez uprzedniego kliknięcia w przycisk do uploadowania. W tym przypadku dialog powinien mieć tytuł: "Wyjście z aplikacji".

Materiały pomocnicze:
- https://github.com/afollestad/material-dialogs

#### Termin realizacji zadania: 29.04.2021 godz. 23.59

Proszę zapoznać się dobrze z opisem narzędzia Heatmap: https://developers.google.com/maps/documentation/android-sdk/utility/heatmap. Zdobyta w tym zakresie wiedza będzie potrzebna na kolejnych zajęciach. 


# Laboratorium 8

Tematy:

- Integracja z REST API
- Programowanie reaktywne
- Heatmap

<b>Zadanie 8 (3p).</b> Po wybraniu budynku z komponentu bottom sheet, dodatkowo zwizualizować poziom zatłoczenia z wykorzystaniem narzędzia Heatmap. Dane należy pobrać z https://laborki-7e3b1.firebaseio.com/galeries.json. Zadbać o obsługę błędów.

Materiały pomocnicze:
- https://square.github.io/retrofit/


#### Termin realizacji zadania: 06.05.2021 godz. 23.59


# Laboratorium 9

Tematy:

- Menu w toolbarze
- STT (Speech To Text)

<b>Zadanie 9 (3p).</b> Po załadowaniu mapy proszę przybliżyć kamerę w taki sposób, aby cała Warszawa była widoczna. Zablokować możliwość zmniejszania zooma do wartości mniejszej niż początkowa (widok miasta). Dodać ikonę mikrofonu do toolbara. Po kliknięciu w mikrofon wyświetlić dialog STT. Proszę obsłużyć następujące hasła: "markety", "banki", "fast-foody". Po wykryciu hasła, mają się pokazać markery dla aktywnej kategorii. U dołu ekranu ma się wyświetlać tylko 1 przycisk - do rozwijania listy z galeriami. Pozostałe przyciski (market, fast-food, bank) mają być widoczne dopiero po wybraniu galerii z listy. Proszę ukrywać przyciski (market, fast-food, bank) w momencie znikania panelu do wyboru piętra.

Materiały pomocnicze:

- https://developer.android.com/reference/android/speech/RecognizerIntent#ACTION_RECOGNIZE_SPEECH

#### Termin realizacji zadania: 13.05.2021 godz. 23.59


# Laboratorium 10

Temat: Implementacja kompasu


# Laboratorium 11

Tematy:

- Rodzaje serwisów 
- Klasa Service
- Zarządzanie usługami
- Komunikacja z usługami
- Zabezpieczenie przed anulowaniem operacji

Materiały pomocnicze:

- https://developer.android.com/guide/components/services


# Laboratorium 12 - Kolokwium


# Laboratorium 13 - Konslutacje + poprawa kolokwium


# Ocena końcowa

W ocenie końcowej uwględniane są: działanie aplikacji oraz styl kodu. W przypadku oddania zadania po terminie odejmowany jest 1 punkt.

Skala ocen:

100%-91% - 5<br/>
90%-86%  - 4.5<br/>
85%-71%  - 4<br/>
70%-61%  - 3.5<br/>
60%-51%  - 3<br/>
 <50%    - 2
 
## Punkty z zadań są sumowane z punktami za kolokwium (23 + 10 = 33p).


# Warunki zaliczenia laboratoriów na ocenę dostateczną

1. Zaliczone przynajmniej 5 zadań w ramach aplikacji "Laborki".
2. Wykonanie w ciągu 7 dni dodatkowego zadania.

Przykładowe zadanie dodatkowe: 

Utworzyć aplikację wyświetlającą mapę z zaznaczoną markerem lokalizacją użytkownika.  Informacja o lokalizacji powinna być pobierana cyklicznie co 20 sekund, a następnie zapisywana do pliku w formacie JSON. W pliku mają być przechowywane zawsze tylko najnowsze informacje - co najwyżej 5 ostatnich lokalizacji. Historię lokalizacji należy wizualizować za pomocą heatmapy. Heatmapa powinna być widoczna przez cały czas pod warunkiem, że historia nie jest pusta.  
