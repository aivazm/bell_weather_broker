package bell.yahooweather.sender;

import bell.commonmodel.model.WeatherView;

/**
 * Класс для отправки сообщений в очередь WeatherToDBQueue.
 */
public interface Sender {
    /**
     * Метод направляет сообщение weatherView в очередь WeatherToDBQueue
     * @param weatherView
     */
    void sendMessage(WeatherView weatherView);
}
