package com.google.mediapipe.examples.llminference.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.mediapipe.examples.llminference.model.InferenceModel
import com.google.mediapipe.examples.llminference.model.ModelLoadFailException
import com.google.mediapipe.examples.llminference.model.ModelSessionCreateFailException
import com.google.mediapipe.examples.llminference.R
import kotlinx.coroutines.*
import okhttp3.OkHttpClient

private class MissingAccessTokenException :
    Exception("Please try again after sign in")

private class UnauthorizedAccessException :
    Exception("Access denied. Please try again and grant the necessary permissions.")

private class MissingUrlException(message: String) :
    Exception(message)

private const val UNAUTHORIZED_CODE = 401

@Composable
internal fun LoadingRoute(
    onModelLoaded: () -> Unit = { },
    onGoBack: () -> Unit = {}
) {
    val context = LocalContext.current.applicationContext
    var errorMessage by remember { mutableStateOf("") }

    var progress by remember { mutableStateOf(0) }
    var isDownloading by remember { mutableStateOf(false) }
    var job: Job? by remember { mutableStateOf(null) }
    val client = remember { OkHttpClient() }

    if (errorMessage != "") {
        ErrorMessage(errorMessage, onGoBack)
    } else if (isDownloading) {
        DownloadIndicator(progress) {
            job?.cancel()
            isDownloading = false
        }
    } else {
        LoadingIndicator()
    }

    LaunchedEffect(Unit) {
        job = launch(Dispatchers.IO) {
            try {
                if (!InferenceModel.modelExists(context)) {
                    if (InferenceModel.model.url.isEmpty()) {
                        throw MissingUrlException("Please manually copy the model to ${InferenceModel.model.path}")
                    }
                    isDownloading = true
                }

                InferenceModel.resetInstance(context)
                // Notify the UI that the model has finished loading
                withContext(Dispatchers.Main) {
                    onModelLoaded()
                }
            } catch (e: MissingAccessTokenException) {
                errorMessage = e.localizedMessage ?: "Unknown Error"
            } catch (e: MissingUrlException) {
                errorMessage = e.localizedMessage ?: "Unknown Error"
            } catch (e: UnauthorizedAccessException) {
                errorMessage = e.localizedMessage ?: "Unknown Error"
            } catch (e: ModelSessionCreateFailException) {
                errorMessage = e.localizedMessage ?: "Unknown Error"
            } catch (e: ModelLoadFailException) {
                errorMessage = e.localizedMessage ?: "Unknown Error"
                // Remove invalid model file
            } catch (e: Exception) {
                val error = e.localizedMessage ?: "Unknown Error"
                errorMessage =
                    "${error}, please manually copy the model to ${InferenceModel.model.path}"
            } finally {
                isDownloading = false
            }
        }
    }
}

@Composable
fun DownloadIndicator(progress: Int, onCancel: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Downloading Model: $progress%",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CircularProgressIndicator(progress = { progress / 100f })
        Button(onClick = onCancel, modifier = Modifier.padding(top = 8.dp)) {
            Text("Cancel")
        }
    }
}

@Composable
fun LoadingIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.loading_model),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(
    errorMessage: String,
    onGoBack: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onGoBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Go Back")
        }
    }
}

