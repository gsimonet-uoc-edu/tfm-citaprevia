// =================================================================
// 1. Configuració de les Rutes
// IMPORT: Has de canviar 'localhost:8080' i 'localhost:8081' pels ports o dominis reals!
// =================================================================
const API_URL = "http://localhost:8080/citapreviaapi/actuator"; 
const FRONT_URL = "http://localhost:8081/citapreviafront/actuator"; 
const REFRESH_INTERVAL = 5000; // 5 segons

// =================================================================
// 2. Funcions Auxiliars de Fetching i Parsing
// =================================================================

/**
 * Funció per obtenir i mostrar l'estat de salut (UP/DOWN).
 * @param {string} url - URL base de l'Actuator del servei.
 * @param {string} elementId - ID del <span> on mostrar l'estat.
 */
async function fetchHealth(url, elementId) {
    const element = document.getElementById(elementId);
    try {
        const response = await fetch(`${url}/health`);
        const data = await response.json();
        const status = data.status;

        element.textContent = status;
        element.className = (status === 'UP') ? 'status-up' : 'status-down';

    } catch (error) {
        console.error(`Error en fetchHealth des de ${url}:`, error);
        element.textContent = "CONN. ERROR";
        element.className = 'status-down';
    }
}

/**
 * Funció genèrica per obtenir una mètrica específica i formatar-la.
 * @param {string} url - URL base de l'Actuator del servei.
 * @param {string} metricName - Nom de la mètrica (ex: 'jvm.memory.used').
 * @returns {Promise<number|null>} El valor de la mètrica o null en cas d'error.
 */
async function getMetricValue(url, metricName) {
    try {
        const response = await fetch(`${url}/metrics/${metricName}`);
        const data = await response.json();
        if (data.measurements && data.measurements.length > 0) {
            return data.measurements[0].value;
        }
        return null;
    } catch (error) {
        // console.error(`Error al obtenir la mètrica ${metricName} des de ${url}:`, error);
        return null;
    }
}


/**
 * Funció per obtenir i mostrar les mètriques de rendiment.
 * @param {string} url - URL base de l'Actuator del servei.
 * @param {string} memId - ID del <span> de la memòria.
 * @param {string} [uptimeId] - ID opcional del <span> de l'Uptime.
 * @param {string} [threadsId] - ID opcional del <span> dels Threads.
 */
async function fetchPerformanceMetrics(url, memId, uptimeId, threadsId) {
    
    // Memòria Heap
    const memUsedBytes = await getMetricValue(url, 'jvm.memory.used');
    if (memUsedBytes !== null) {
        const memUsedMB = (memUsedBytes / 1048576).toFixed(2);
        document.getElementById(memId).textContent = `${memUsedMB} MB`;
    } else {
        document.getElementById(memId).textContent = "N/A";
    }

    // Uptime
    if (uptimeId) {
        const uptimeSeconds = await getMetricValue(url, 'process.uptime');
        if (uptimeSeconds !== null) {
            const hours = (uptimeSeconds / 3600).toFixed(1);
            document.getElementById(uptimeId).textContent = `${hours} hores`;
        } else {
            document.getElementById(uptimeId).textContent = "N/A";
        }
    }

    // Threads Vius
    if (threadsId) {
        const liveThreads = await getMetricValue(url, 'jvm.threads.live');
        document.getElementById(threadsId).textContent = (liveThreads !== null) ? liveThreads : "N/A";
    }
}

/**
 * Funció per obtenir i mostrar la versió (des de /info).
 * @param {string} url - URL base de l'Actuator del servei.
 * @param {string} elementId - ID del <span> de la versió.
 */
async function fetchInfo(url, elementId) {
    try {
        const response = await fetch(`${url}/info`);
        const data = await response.json();
        // Assumeix que la versió està a data.app.version o similar
        const version = data.app ? data.app.version : "N/A"; 
        document.getElementById(elementId).textContent = version || "Desconeguda";
    } catch (error) {
        document.getElementById(elementId).textContent = "N/A";
    }
}


// =================================================================
// 3. Funció Principal i Execució
// =================================================================

function updateDashboard() {
    // 1. Actualitza el servidor API
    fetchHealth(API_URL, 'api-health');
    fetchInfo(API_URL, 'api-version');
    fetchPerformanceMetrics(API_URL, 'api-mem', 'api-uptime', 'api-threads');

    // 2. Actualitza l'aplicació Web Front
    fetchHealth(FRONT_URL, 'front-health');
    fetchPerformanceMetrics(FRONT_URL, 'front-mem', null, null);
    
    // 3. Actualitza el timestamp
    const now = new Date();
    document.getElementById('timestamp').textContent = `Última actualització: ${now.toLocaleTimeString('ca-ES')}`;
}

// Inicia l'actualització del Dashboard
updateDashboard();
// Programa l'actualització periòdica
setInterval(updateDashboard, REFRESH_INTERVAL);