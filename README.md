# Laboratorium 1 

Tematy:

- Przygotowanie środowiska developerskiego
- Podstawy języka Kotlin
- Zapoznanie się z aplikacją "Laborki" 
- Tworzenie dialogów z wykorzystaniem klasy AlertDialog
- Podstawy obsługi stosu

## Instalacja sterowników ADB

1. W Menadżerze urządzęń wybrać nazwę podłączonego telefonu<br/> (Inne urządzenia) oraz kliknąć w opcję "Aktualizuj oprogramowanie sterownika" -> Przeglądaj mój komputer...

2. Wskazać ścieżkę do zainstalowanych sterowników (android_sdk_location\extras\google\usb_driver).

3. Zainstalować wybrabe sterowniki.

Materiały pomocnicze:

- https://www.download.net.pl/jak-zainstalowac-sterowniki-usb-i-adb-do-androida-w-windows/n/4061/

# Laboratorium 2

Tematy: 

- Architektura MVP
- Wstrzykiwanie zależności z wykorzystaniem biblioteki Dagger 2
- Wzorzec Repository

Zadanie 1 (2 punkty). Dodać obsługę przycisku "Wstecz". Przed wyjściem z aplikacji powinien pojawić się dialog z prośbą o potwierdzenie decyzji. Klasa AlertDialogUtils.kt powinna być dołączona do fragmentu za pomocą mechanizmu DI. 
Uwaga: Upewnić się, że dialog jest wyświetlany tylko w przypadku, gdy na stosie znajduje się jeden fragment. 

Zadanie 2 (1 punkt). Dodać repozytorium umożliwiające przechowywanie informajci o podanym mailu oraz współrzędnych geograficznych.

Zadanie 3 (2 punkty). Dodać obsługę przycisku "Dalej". Po jego kliknięciu podany adres email powinien zostać zapisany w repozytorium.  Obsłużyć klawiaturę systemową w taki sposób, żeby przycisk "Dalej" był widoczny w momencie wpisywania tekstu. 

Zadanie 4 (2 punkty). Utworzyć ekran powitalny (tak zwany splash screen). Każda z grup powinna zaprezentować inne rozwiązanie. 


#### Termin realizacji zadań 1-3: 02.05.2019 godz. 21.00
#### Termin realizacji zadania 4: 30.05.2019 godz. 21.00


# Laboratorium 3

Tematy:

- Uprawnienia
- Obsługa GPS
- Dobre praktyki nawigowania po aplikacji
- Tworzenie nowoczesnych kontrolek

Zadanie 1 (2 punkty). Utworzyć widok umożliwiający podanie współrzędnych geograficznych (coordinates.png). Po wyświetleniu ekranu użytkownik powinien zostać poproszony o udzielenie dostępu do lokalizacji. Po włączeniu modułu GPS, współrzędne użytkownika powinny zostać automatycznie wyświetlone w formularzu. 

Zadanie 2 (2 punkty). Dodać globalny mechaznim umożliwiający nawigowanie pomiędzy ekranami. Następnie rozszerzyć obsługę przycisku "Dalej" w klasie RecipientDataFragment w taki sposób, aby po jego kliknięciu wyświetlił się ekran ze współrzędnymi. 

Zadanie 3 (2 punkty). Poprawić wygląd komponentu EditText zgodnie z dołączonym zdjęciem (custom_edit_text.png).

Zadanie 4 (2 punkty) ???

#### Termin realizacji zadań: 09.05.2019 godz. 21.00


# Laboratorium 4

Tematy: 

- Techniki współdzielenia danych pomiędzy różnymi aplikacjami
- Tworzenie list za pomocą RecyclerView
- Integracja z Google Maps API

Zadanie 1 (3 punkty). Obsłużyć przycisk "Share" w każdym z opsianych poniżej przypadków:

a) Użytkownik nie podał adresu email -  Wyświelić menu systemowe umożliwiające wybór aplikacji, za pomocą której mogą zostać przesłane współrzędne.

b) użytkownik podał wszystkie dane (adres email oraz współrzędne) - Otworzyć aplikację umożliwiającą wysyłanie emaila.

c) Użytkownik nie podał żadnych informacji - brak akcji

W pierwszym oraz drugim przypadku należy automatycznie uzupełnić dane w formacie: "longitude: xxx; latitude: xxx".

Zadanie 2 (2 punkty). Utworzyć ekran z listą prezentującą sprzęt wojskowy (mapa_1.png). Informacje o sprzęcie można pobrać z repozytorium. Wybrany element listy powinien być w jakiś sposób wyróżniony.  

Zadanie 3 (2 punkty). Zintegrować aplikację z Google Maps API. Dodać mapę nad listą prezentującą sprzęt wojskowy (mapa_1.png). Mapa powinna prezentować obszar określony za pomocą współrzędnych pobranych na poprzednim ekranie. 

Zadanie 4 (2 punkty) ???

#### Termin realizacji zadań: 16.05.2019 godz. 21.00


# Laboratorium 5

Tematy:

- Interakcja z mapą
- Obsługa markerów
- Programowanie reaktywne z wykorzystaniem biblioteki RxJava
- Obsługa usług zewnętrznych z wykorzystaniem biblioteki Retrofit

Zadanie 1 (2 punkty). Po dłuższym przytrzymaniu punktu na mapie, w jego miejscu powinien pojawić się wybany sprzęt. 

Zadanie 2 (3 punkty). Po kliknięciu w marker powinien wyświetlić się dialog prezentujący szczegóły techniczne urządzenia. Dodać ikonę umożliwiającą usuwanie zaznaczonego urządzenia z mapy (mapa_1.png). 

Zadanie 3 (3 punkty). Dodać mechanizm zabezpieczający przed umieszczaniem w wodzie obiektów z kategorii sił lądowych. W analogiczny sposób należy uniemożliwić umieszczanie na lądzie obiektów z kategorii marynarki wojennej. W przypadku naruszenia tych zasad, powinien wyświetlić się stosowny komunikat (mapa_2.png). W celu wykonania ćwiczenia należy skorzystać z API o nazwie On Water (https://onwater.io/).

Zadanie 4 (2 punkty) ???

#### Termin realizacji zadań: 30.05.2019 godz. 21.00


## Ćwiczenia dla chętnych (możliwość podwyższenia oceny końcowej)

Zadanie 1 (3 punkty). Napisać testy jednostkowe w celu upewnienia się, że wszystkie mechanizmy w aplikacji działają prawidłowo.

Zadanie 2 (3 punkty). Napisać testy sprawdzające poprawność działania elementów UI w aplikacji.

# Kryteria oceny końcowej

Za wszystkie zadania można otrzymać łącznie 42 punkty. Uwględniane są: funkcjonalność oraz styl kodu. W przypadku oddania zadania po terminie odejmowane są 2 punkty.

Poniżej przedstawiono wymaganą ilość punktów na poszczególne oceny:

- 34p - 30p - 5
- 29p - 27p - 4.5
- 26p - 24p - 4
- 23p - 21p - 3.5
- 20p - 18p - 3
- 17p i mniej - 2
