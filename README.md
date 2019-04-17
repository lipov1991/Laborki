# Instalacja sterowników ADB

1. W Menadżerze urządzęń należy wybrać nazwę podłączonego telefonu<br/> (Inne urządzenia) oraz kliknąć w opcję "Aktualizuj oprogramowanie sterownika" -> Przeglądaj mój komputer...

2. Wskazać ścieżkę do zainstalowanych sterowników (android_sdk_location\extras\google\usb_driver).

3. Zainstalować wybrabe sterowniki.


# Laboratorium 1

Cel: 

- Przygotowanie środowiska developerskiego.
- Zapoznanie się z aplikacją, która będzie realizowana podczas zajęć. 
- Krótkie omówienie języka Kotlin.

Zadanie 1 (2p). Dodać obsługę przycisku "Wstecz". Przed wyjściem z aplikacji powinien pojawić się dialog z prośbą o potwierdzenie decyzji. Uwaga: Upewnić się, że dialog jest wyświetlany tylko w przypadku, gdy na stosie znajduje się jeden fragment.

Zadanie 2 (2p). Dodać obsługę przycisku "Dalej". Podany adres email powinien być przesłany z fragmentu do aktywności, a następnie zapisany w prezenterze MainPresenter. Obsłużyć klawiaturę systemową w taki sposób, żeby przycisk "Dalej" był widoczny w momencie wpisywania tekstu.

Wskazówka: W celu przesłania danych z fragmentu do aktywności należy wykorzystać interfejs ShareDataListener. Proszę zdefiniować w nim następującą funkcję: fun onEmailChanged(emailAddress: String). Po odczytaniu wartości parametru emailAddress w klasie MainActivity, należy ją zapisać w prezenterze: presenter.onEmailChanged(emailAddress). 

#### Termin realizacji zadań: 25.04.2019 godz. 21.00


# Laboratorium 2

Cel:

- Omówienie wykorzystanej w projekcie architektury MVP.
- Stworzenie nowoczesnej kontrolki EditText.
- Omówienie biblioteki Dagger 2 i wykorzystanie jej w projekcie.
- Omówienie i wykorzystanie w projekcie wzorca Repository.

Zadanie 1 (2p). Poprawić wygląd komponentu EditText zgodnie z dołączonym zdjęciem (custom_edit_text.png).

Zadanie 2 (2p). Dodać do projektu bibliotekę Dagger 2, a następnie odseparować komponenty aplikacji wykorzystując technikę wstrzykiwania zależności.

Zadanie 3 (3p). Utworzyć widok umożliwiający podanie współrzędnych geograficznych (coordinates.png). Po wyświetleniu ekarnu użytkownik powinien zostać poproszony o udzielenie dostępu do lokalizacji. Po włączeniu modułu GPS, formularz powinien zostać automatycznie uzupełniony współrzędnymi użytkownika. 

Zadanie 4 (2p). Dodać repozytorium umożliwiające przechowywanie informajci o podanym mailu oraz współrzędnych geograficznych.
Przenieść wszystkie dane przechowywane w prezenterze do repozytorium.

#### Termin realizacji zadań: 02.05.2019 godz. 21.00


# Laboratorium 3

Cel:

- Poznanie technik współdzielenia danych pomiędzy różnymi aplikacjami.

Zadanie 1 (2p). Po kliknięciu w ikonę "Share" powinno pojawić się menu systemowe umożliwiające wybranie aplikacji, przez którą mają zostać przesłane współrzędne. Obsłużyć wszystkie opisane poniżej przypadki:

- użytkownik nie podał żadnych informacji -> brak akcji
- użytkownik nie podał adresu email -> wyświetlenie menu systemowego
- użytkownik nie podał współrzędnych-> uruchomienie aplikacji do zarządzania wiadomościami email. 

