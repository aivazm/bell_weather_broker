package bell.adminapi.sender;

/**
 * Класс для отправки сообщений в очередь CityWeatherQueue.
 */
public interface Sender {
    /**
     * Метод направляет сообщение txt в очередь CityWeatherQueue
     * @param txt
     */
    void sendMessage(String txt);
}
