# Instalacja sterowników ADB

1. W Menadżerze urządzęń wybrać nazwę podłączonego telefonu<br/> (Inne urządzenia) oraz kliknąć w opcję "Aktualizuj oprogramowanie sterownika" -> Przeglądaj mój komputer...

2. Wskazać ścieżkę do zainstalowanych sterowników (android_sdk_location\extras\google\usb_driver).

3. Zainstalować wybrabe sterowniki.


# Laboratorium 1

Cele: 

- Przygotowanie środowiska developerskiego.
- Krótkie omówienie języka Kotlin.
- Zapoznanie się z aplikacją "Laborki". 

Zadanie 1. Dodać obsługę przycisku "Wstecz". Przed wyjściem z aplikacji powinien pojawić się dialog z prośbą o potwierdzenie decyzji. Należy upewnić się, że dialog jest wyświetlany tylko w przypadku, gdy na stosie znajduje się jeden fragment.

Zadanie 2. Dodać obsługę przycisku "Dalej". Podany adres email powinien być przesłany z fragmentu do aktywności, a następnie zapisany w prezenterze MainPresenter. Obsłużyć klawiaturę systemową w taki sposób, żeby przycisk "Dalej" był widoczny w momencie wpisywania tekstu.

Wskazówka: W celu przesłania danych z fragmentu do aktywności należy wykorzystać interfejs ShareDataListener. Proszę zdefiniować w nim  funkcję o następującej strukturze: fun onEmailChanged(email: String). Przekazany parametr należy zapisać w prezenterze. 

#### Termin realizacji zadań: 02.05.2019 godz. 21.00


# Laboratorium 2

Cele:

- Omówienie wykorzystanej w projekcie architektury MVP.
- Stworzenie nowoczesnej kontrolki EditText.
- Omówienie biblioteki Dagger 2 i wykorzystanie jej w projekcie.

Zadanie 1. Poprawić wygląd komponentu EditText zgodnie z dołączonym zdjęciem (custom_edit_text.png).

Zadanie 2. Dodać do projektu bibliotekę Dagger 2, a następnie odseparować komponenty aplikacji wykorzystując technikę wstrzykiwania zależności.

Zadanie 3. Utworzyć widok umożliwiający podanie współrzędnych geograficznych (coordinates.png). Po wyświetleniu ekarnu użytkownik powinien zostać poproszony o udzielenie dostępu do lokalizacji. Po włączeniu modułu GPS, współrzędne użytkownika powinny zostać automatycznie wyświetlone w formularzu. 


#### Termin realizacji zadań: 09.05.2019 godz. 21.00


# Laboratorium 3

Cele: 

- Omówienie i wykorzystanie w projekcie wzorca Repository.
- Poznanie technik współdzielenia danych pomiędzy różnymi aplikacjami.

Zadanie 1. Dodać repozytorium umożliwiające przechowywanie informajci o podanym mailu oraz współrzędnych geograficznych.
Przenieść wszystkie dane przechowywane w prezenterze do repozytorium.

Zadanie 2. Po kliknięciu w ikonę "Share" powinno pojawić się menu systemowe umożliwiające wybranie aplikacji, za pomocą której mają zostać przesłane współrzędne. Obsłużyć opisane poniżej przypadki:

- użytkownik nie podał żadnych informacji -> brak akcji
- użytkownik nie podał adresu email -> wyświetlenie menu systemowego

#### Termin realizacji zadań: 16.05.2019 godz. 21.00


# Laboratorium 4

Cele: 

- Poznanie techniki tworzenia listy za pomocą RecyclerView.
- Integracja z Google Maps API.

Zadanie 1. Zintegrować aplikację z Google Maps API. Mapa powinna prezentować obszar określony za pomocą współrzędnych pobranych na poprzednim ekranie. 

Zadanie 2. Stworzyć ekran składający się z mapy oraz listy obiektów wojskowych (mapa_1.png). Lista powinna wyświetlać obiekty zapisane w dostępnym repozytorium. Wybrany element listy powinien być w jakiś sposób wyróżniony. Po dłuższym przytrzymaniu punktu na mapie, w jego miejscu powinien pojawić się wybany obiekt. 

#### Termin realizacji zadań: 23.05.2019 godz. 21.00


# Laboratorium 5

Cele:

- Nauczenie się prostej interakcji z mapą.
- Obsługa markerów.
- Omówienie i wykorzystanie w projekcie paradygmatu programowania reaktywnego.
- Poznanie bibliotek RxJava oraz Retrofit.
- Nauczenie się dobrych praktyk komunikacji z zewnętrznymi usługami.

Zadanie 1. Po kliknięciu w obiekt, w jego miejscu powinien wyświetlić się dialog prezentujący szczegóły techniczne. Dodać ikonę umożliwiającą usuwanie zaznaczonego obiektu z mapy (mapa_1.png).

Zadanie 2. Dodać mechanizm zabezpieczający przed umieszczaniem w wodzie obiektów z kategorii sił lądowych. W analogiczny sposób należy uniemożliwić umieszczanie na lądzie obiektów z kategorii marynarki wojennej. W przypadku naruszenia tych zasad, powinien wyświetlić się stosowny komunikat (mapa_2.png). W celu wykonania ćwiczenia należy skorzystać z API o nazwie On Water (https://onwater.io/).

#### Termin realizacji zadań: 30.05.2019 godz. 21.00


# Kryteria oceny końcowej

Za każde z zadań można otrzymać łącznie 2 punkty (funkcjonalność - 1p, styl kodu - 1p). W przypadku oddania zadania po terminie można otrzymać tylko 1 punkt. Za cały projekt można otrzymać łącznie 22 punkty. 
Poniżej przedstawiono wymaganą ilość punktów na poszczególne oceny:

- 22p - 20p - bardzo dobry
- 19p - 17p - dobry
- 16p - 13p - dostateczny
- 12p - 10p - dopuszczający
- 9p i mniej - niedostateczny
