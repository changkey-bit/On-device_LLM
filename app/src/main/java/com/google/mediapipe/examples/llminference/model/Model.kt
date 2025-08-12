package com.google.mediapipe.examples.llminference.model


import com.google.mediapipe.tasks.genai.llminference.LlmInference.Backend

// NB: Make sure the filename is *unique* per model you use!
// Weight caching is currently based on filename alone.
enum class Model(
    val path: String,
    val url: String,
    val licenseUrl: String,
    val needsAuth: Boolean,
    val preferredBackend: Backend?,
    val thinking: Boolean,
    val temperature: Float,
    val topK: Int,
    val topP: Float,
) {
    GEMMA_2_2B_IT_GPU(
        path = "/data/local/tmp/llm/gemma3-1B-it-int4.task",
        url = "https://huggingface.co/litert-community/Gemma2-2B-IT/resolve/main/Gemma2-2B-IT_multi-prefill-seq_q8_ekv1280.task",
        licenseUrl = "https://huggingface.co/litert-community/Gemma2-2B-IT",
        needsAuth = true,
        preferredBackend = Backend.CPU,
        thinking = false,
        temperature = 0.6f,
        topK = 50,
        topP = 0.9f
    )
}
