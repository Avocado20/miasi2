AirCraft Agent

Opis aplikacji:

Aplikacja symuluje działanie lotniska w architekturze agentowej.
Poszczególni agenci symulowani są przez wątki działające niezależnie od siebie.
Metoda AirCraftSystem -> initializeAirCraftAgents() decyduje, ilu agentów samolotów ma powstać.

"Stan środowiska" jest symulowany przez fakt, że każdy AirCraft agent ma referencje do reszty agentów. Metoda getInformationFromOtherAgents() symuluje pobierania informacji o innych agentach.

Agenci w systemie.
1. AirCraft agent: agent samolotu, który wyraża chęć startowania/lądowania/latania. Na przestrzeni czasu zmienia on cel działania.
2. Weather agent: agent udzielający samolotom informacj, jak długo potrwa procedura startu/lądowania w oparciu o ich podstawowy czas lądowania.
 W zależności od kierunku wiatru, czas może się wydłużyć.
3. AirPort agent: agent odpowiadający za zezwalania na start/ląðowanie samolotów. Zgoda zostaje wyrażona tylko w przypadku, gdy chcący wylądować samolot, posiada najmniejszą ilośc paliwa spośród proszących o zgodę.


Jak rostrzygana jest kolejność: AirCraft agent, gdy zdecyduje o ladowaniu, musi zapytać inne samoloty o ich poziom paliwa.
Jeśli samolot posiada najmniej paliwa spośród proszących o zgodę, ustawiana jest w nim flaga amILandingNext.
Samolot w tym momencie przechodzi w tryb oczekiwania na pozwolenie. Jeśli airPortAgent ma ustawioną zmienną airCraftLandingID na 0, samolot zaczyna procedurę lądowania.
Czas trwania został już wcześniej ustalony.

W przypadku startu, kolejność jest rostrzygana podobnie, jednak decyduje tylko kolejność zgłoszeń, gdyż samoloty wtedy nie zmieniają ilości paliwa.
Jeśli samolot osiągnie ilość paliwa = 0, samolot drukuje na ekran wiadomość o swojej katastrofie.

Podstawowy model celów:
Cel nadrzędny: Zarządzanie startem i lądowaniami.
Cele podrzędne:
            Lądowanie, w ramach którego airCraft agent może mieć cel ustawienia się w kolejce do lądowania
            Start w ramach którego airCraft agent może mieć cel ustawienia się w kolejce do startu
            Latanie, w ramach którego może zmienić cel na lądowanie

Model ról:
    AirCraft : Latanie, Lądowanie, Startowanie, Prosze o zezwolenia,
    AirPort: zezwalanie na lądowanie/start,
    Weather: Obliczanie właściwego czasu sekwencji lądowania/startu.


Model planów(stanów):
    Lataj: samolot może przejść do stanu "proś o zezwolenie na lądowanie",
    Proś o zezwolenie na lądowanie: może przejść do stanu "Ląduj",
    Ląduj: może przejść do stanu "Wylądował",
    Wylądował: może przejść do stanu: "Poproś o zezwolenie na start",
    Poproś o zezwolenie na start: Może przejść do stanu: "Startuj",
    Startuj: Może przejść do stanu: "Lataj"

Modele protokołów:
    AirCraftAgent -> zapytajOCzasLadowania -> WeatherAgent
    AirCraftAgent -> poprośOZezwolenie -> AirportAgent
    AirCraftAgent -> zapytajOPoziomPaliwa -> AirCraftAgent
    WeatherAgent -> obliczCzasSekwencji -> AirCraftAgent
    AirportAgent -> zezwólNaStartLądowanie -> AirCraftAgent
