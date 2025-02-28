package org.lebastudios.restapiclientexamen.http;

import java.net.http.HttpClient;

public class Client
{
    public static HttpClient defaultClient()
    {
        return HttpClient.newHttpClient();
    }
    
/*

public static void main(String[] args)
    {
        HttpClient client = defaultClient();

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString("HI")).uri(URI.create("/")).build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        response.statusCode();
    }

package org.lebastudios.theroundtable.communications;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import javafx.application.Platform;
import lombok.SneakyThrows;
import org.lebastudios.theroundtable.AppTask;
import org.lebastudios.theroundtable.config.data.SettingsData;
import org.lebastudios.theroundtable.env.Variables;
import org.lebastudios.theroundtable.MainStageController;
import org.lebastudios.theroundtable.TheRoundTableApplication;
import org.lebastudios.theroundtable.config.data.JSONFile;
import org.lebastudios.theroundtable.config.data.PluginsConfigData;
import org.lebastudios.theroundtable.plugins.pluginData.PluginData;
import org.lebastudios.theroundtable.ui.TaskManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

    public class ApiRequests
    {
        private static final String BASE_URL = switch (Variables.getEnvironmentType())
        {
            case TEST, DEV -> Variables.getTestServerUrl();
            case PROD -> "https://lebastudios.org/api/v1/theroundtable";
            default -> throw new IllegalStateException("Unexpected value: " + Variables.getEnvironmentType());
        };

        public static boolean availableUpdate()
        {
            try (var client = AppHttpClient.getInstance().newClient())
            {
                var request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/update/available?version=" + TheRoundTableApplication.getAppVersion()))
                        .build();

                var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                HashMap<String, Boolean> responseMap = new Gson().fromJson(response.body(), HashMap.class);

                return responseMap.get("response");
            }
            catch (Exception e)
            {
                System.err.println("An error ocurred while trying to get the available update: " + e.getMessage());
                return false;
            }
        }

        public static void getLastAppVersion()
        {
            downloadFile(
                    BASE_URL + "/update/desktop-app.jar",
                    TheRoundTableApplication.getAppDirectory() + "/bin",
                    "Downloading app update"
            );
        }

        @SneakyThrows
        private static void downloadFile(String fileURL, String saveDir, String taskTitle)
        {
            URL url = new URI(fileURL).toURL();

            var proxyConf = new JSONFile<>(SettingsData.class).get().proxyData;

            HttpURLConnection httpConn;

            if (proxyConf != null && proxyConf.usingProxy)
            {
                String proxyAddress = proxyConf.proxyAddress;
                int proxyPort = proxyConf.proxyPort;

                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort));
                httpConn = (HttpURLConnection) url.openConnection(proxy);
            }
            else
            {
                httpConn = (HttpURLConnection) url.openConnection();
            }

            // Verifica la respuesta del servidor
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);

                final var downloadingTask = createDownloadingTask(saveDir, fileName, httpConn, taskTitle);
                TaskManager.getInstance().startNewTaskWithProgressBar(downloadingTask, false);
            }
            else
            {
                System.err.println("No se pudo descargar el archivo. Código de respuesta: " + responseCode);
            }
        }

        private static AppTask createDownloadingTask(String saveDir, String fileName, HttpURLConnection httpConn,
                String taskTitle)
        {
            var saveFile = new File(saveDir + File.separator + fileName);
            var tempFile = new File(TheRoundTableApplication.getUserDirectory() + "/.tmp/" + fileName);

            if (!tempFile.exists()) tempFile.getParentFile().mkdirs();

            return new AppTask("download.png")
            {
                @SneakyThrows
                @Override
                protected Void call()
                {
                    var totalBytes = httpConn.getContentLengthLong();
                    updateTitle(taskTitle);
                    updateMessage("Downloading...");

                    try (InputStream inputStream = httpConn.getInputStream();
                         FileOutputStream outputStream = new FileOutputStream(tempFile))
                    {
                        int bytesRead;
                        byte[] buffer = new byte[4096];
                        while ((bytesRead = inputStream.read(buffer)) != -1)
                        {
                            outputStream.write(buffer, 0, bytesRead);

                            if (totalBytes == -1) continue;

                            updateProgress(outputStream.getChannel().size(), totalBytes);
                        }

                        outputStream.flush();
                    }
                    catch (Exception e)
                    {
                        System.err.println("An error ocurred while downloading the file: " + e.getMessage());
                    }

                    httpConn.disconnect();

                    updateMessage("Saving file...");
                    saveFile.getParentFile().mkdirs();
                    Files.copy(tempFile.toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Files.delete(tempFile.toPath());

                    Platform.runLater(() -> MainStageController.getInstance().requestRestart());

                    return null;
                }
            };
        }

        public static PluginData[] getPluginsDataAvailable()
        {
            try (var client = AppHttpClient.getInstance().newClient())
            {
                var request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/plugins/pluginsData"))
                        .build();

                var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200)
                {
                    System.err.println(
                            "An error ocurred while trying to get the available plugins data: " + response.body());
                    return new PluginData[0];
                }

                return new Gson().fromJson(response.body(), PluginData[].class);
            }
            catch (Exception e)
            {
                System.err.println("An error ocurred while trying to get the available plugins data: " + e.getMessage());
                return null;
            }
        }

        public static PluginData getServerPluginData(String pluginId)
        {
            try (var client = AppHttpClient.getInstance().newClient())
            {
                var request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/plugins/" + pluginId + ".jar.json"))
                        .build();

                var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200)
                {
                    System.err.println("An error ocurred while trying to get the plugin data: " + response.body());
                    return null;
                }

                return new Gson().fromJson(response.body(), PluginData.class);
            }
            catch (Exception e)
            {
                System.err.println("An error ocurred while trying to get the plugin data: " + e.getMessage());
                return null;
            }
        }

        public static boolean pluginNeedUpdate(PluginData pluginData)
        {
            try (var client = AppHttpClient.getInstance().newClient())
            {
                var request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/plugins/" + pluginData.pluginId + "/updateable?version=" +
                                pluginData.pluginVersion))
                        .build();

                var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                HashMap<String, Boolean> responseMap = new Gson().fromJson(response.body(), HashMap.class);

                return responseMap.get("response");
            }
            catch (Exception e)
            {
                System.err.println("An error ocurred while trying to get the available update: " + e.getMessage());
                return false;
            }
        }

        public static void updatePlugin(PluginData pluginData)
        {
            downloadFile(
                    BASE_URL + "/plugins/" + pluginData.pluginId + ".jar",
                    new JSONFile<>(PluginsConfigData.class).get().pluginsFolder,
                    "Downloading plugin update (" + pluginData.pluginName + ")"
            );
        }

        public static Boolean isLicenseValid(String license)
        {
            try (var client = AppHttpClient.getInstance().newClient())
            {
                var request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/licenses/validate?license_id=" + license))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

                var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                HashMap<String, Boolean> responseMap = new Gson().fromJson(response.body(), HashMap.class);

                return responseMap.get("message");
            }
            catch (Exception e)
            {
                System.err.println("An error ocurred while trying to validate the license: " + e.getMessage());
                return null;
            }
        }

        public static boolean has_license(String email, String password)
        {
            return false;
        }

        public static boolean login(String email, String password)
        {
            return false;
        }
    }
*/
}
