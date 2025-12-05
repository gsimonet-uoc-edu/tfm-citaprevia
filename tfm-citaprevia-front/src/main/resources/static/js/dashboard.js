// Configuració dels hosts
const API_URL = "http://localhost:8080/citapreviaapi/actuator"; 
const FRONT_URL = "http://localhost:8081/citapreviafront/actuator"; 
const REFRESH_INTERVAL = 5000; // Refrescar cada 5 segons

/**
 * Funció per obtenir i mostrar l'estat de salut (UP/DOWN).
 * @param {string} url - URL Servei Actuator.
 * @param {string} elementId - identificador <span> on mostrar l'estat de salut.
 */
async function getHealth(url, elementId) {
    const element = document.getElementById(elementId);
    try {
        const response = await fetch(`${url}/health`);
        const data = await response.json();
        const status = data.status;

        element.textContent = status;
        element.className = (status === 'UP') ? 'status-up' : 'status-down';

    } catch (error) {
        console.error(`Error en getHealth des de ${url}:`, error);
        element.textContent = "CONN. ERROR";
        element.className = 'status-down';
    }
}

/**
 * Funció genèrica obtenir una mètrica específica
 * @param {string} url - URL Servei Actuator.
 * @param {string} metricName - Nom de la mètrica
 * @returns {Promise<number|null>} El valor de la mètrica, null en cas d'error.
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
        console.error(`Error al obtenir la mètrica ${metricName} des de ${url}:`, error);
        return null;
    }
}


/**
 * Funció per obtenir i mostrar les mètriques de rendiment.
 * @param {string} url - URL Servei Actuator.
 * @param {string} memId - ID del <span> de la memòria.
 * @param {string} [uptimeId] - identificador <span> on mostrar Uptime
 * @param {string} [threadsId] - identificador <span> on mostrar Threads
 */
async function getMetriquesRendiment(url, memId, uptimeId, threadsId) {
    
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
            const hours = (uptimeSeconds / 60).toFixed(1);
            document.getElementById(uptimeId).textContent = `${hours} minuts`;
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
 * Funció per obtenir i mostrar la versió
 * @param {string} url - URL Servei Actuator.
 * @param {string} elementId - identificador <span> on mostrar Version
 */
async function getInfo(url, elementId) {
    try {
        const response = await fetch(`${url}/info`);
        const data = await response.json();
        const version = data.build ? data.build.version : "N/A";      
        document.getElementById(elementId).textContent = version || "Desconeguda";
    } catch (error) {
        console.error("Error en recuperar la versió:", error);
        document.getElementById(elementId).textContent = "N/A";
    }
}


// Funcio que executa el dashboard
function getDashboard() {
	
    // API (back-end)
    getHealth(API_URL, 'api-health');
    getInfo(API_URL, 'api-version');
    getMetriquesRendiment(API_URL, 'api-mem', 'api-uptime', 'api-threads');

    // FRONT (front-end)
    getHealth(FRONT_URL, 'front-health');
    getMetriquesRendiment(FRONT_URL, 'front-mem', null, null);
    
    // Timestamp
    const now = new Date();
    document.getElementById('timestamp').textContent = `Darrera actualització: ${now.toLocaleTimeString('ca-ES')}`;
}

// Inicia Dashboard
getDashboard();
// Refrescar actualització
setInterval(getDashboard, REFRESH_INTERVAL);