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

Zadanie 1 (2p). Poprawić wygląd komponentu EditText zgodnie z dołączonym zdjęciem (custom_edit_text.png).

Zadanie 2 (2p). Dodać do projektu bibliotekę Dagger 2, a następnie odseparować moduły aplikacji wykorzystując technikę wstrzykiwania zależności.

Zadanie 3 ()

#### Termin realizacji zadań: 25.04.2019 godz. 21.00
