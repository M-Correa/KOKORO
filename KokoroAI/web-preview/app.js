// ================= DATA REPOSITORIES =================
const OUTFITS = [
  {
    id: "school_uniform",
    name: "Uniforme Escolar",
    category: "Clásico",
    isVip: false,
    emoji: "🎒",
    accent: "#8B5CF6",
    image: "kokoro_outfit_school.png",
    hairstyle: "Doble Coleta Escolar (Twin-Tails)",
    accessory: "🎀 Lazos rojos de preparatoria",
    desc: "Falda tableada azul marino, cuello marinero tradicional con pañuelo rojo carmesí, medias altas oscuras y mocasines.",
    quote: "¡Listo Matias! Me puse el uniforme escolar. ¿Nos vamos a estudiar juntos hoy?"
  },
  {
    id: "summer_yukata",
    name: "Yukata de Verano",
    category: "Festivo",
    isVip: true,
    emoji: "🌸",
    accent: "#EC4899",
    image: "kokoro_outfit_yukata.png",
    hairstyle: "Moño Festivo con Kanzashi Floral",
    accessory: "🌸 Horquilla tradicional kanzashi",
    desc: "Yukata blanco translúcido con motivos de sakura rosa, faja obi violeta degradada, abanico uchiwa y sandalias geta.",
    quote: "¡Mira qué bonita es esta yukata de verano! Me siento lista para ver los fuegos artificiales contigo."
  },
  {
    id: "casual_hoodie",
    name: "Hoodie Casual",
    category: "Cómodo",
    isVip: true,
    emoji: "☕",
    accent: "#A855F7",
    image: "kokoro_outfit_hoodie.png",
    hairstyle: "Melena Ondulada Suelta",
    accessory: "☕ Taza humeante de té verde",
    desc: "Sudadera oversize lila pastel súper suave y abrigada, shorts cómodos y calcetines térmicos a rayas lavanda.",
    quote: "Aaah, qué comodidad. Con este hoodie oversize podemos quedarnos en casa escuchando Lo-Fi todo el día."
  },
  {
    id: "cat_pajamas",
    name: "Pijama Gatito",
    category: "Dormir",
    isVip: false,
    emoji: "🐾",
    accent: "#F472B6",
    image: "kokoro_outfit_pajama.png",
    hairstyle: "Trenza Lateral con Lazos Rosas",
    accessory: "🐾 Orejitas de gato & Patitas suaves",
    desc: "Enterizo afelpado rosa pastel con capucha de gatito, orejas móviles en 3D, cola esponjosa y pantuflas de garritas.",
    quote: "¡Nya! ¿Te gusta mi pijama de gatito? Prometo no arañarte... ¡solo cuidarte mucho!"
  },
  {
    id: "elegant_maid",
    name: "Traje Maid Elegante",
    category: "Especial",
    isVip: true,
    emoji: "🎀",
    accent: "#6366F1",
    image: "kokoro_outfit_maid.png",
    hairstyle: "Recogido Elegante con Diadema",
    accessory: "🕊️ Cofia de encaje blanco & Lazo",
    desc: "Vestido negro entallado de falda amplia con enagua de tul, delantal blanco con volados de encaje fino y lazo posterior.",
    quote: "Bienvenido a casa, Señor Matias. Su fiel servidora Kokoro está a su completa disposición ♡"
  },
  {
    id: "cyberpunk_cosplay",
    name: "Cosplay Cyberpunk",
    category: "Futurista",
    isVip: true,
    emoji: "⚡",
    accent: "#06B6D4",
    image: "kokoro_outfit_cyberpunk.png",
    hairstyle: "Coleta Alta con Luces Neón",
    accessory: "⚡ Visor holográfico & Auriculares",
    desc: "Chaqueta tornasolada con circuitos de fibra óptica rosa y cian brillante, top ajustado reflectivo y botas tácticas.",
    quote: "¡Protocolo Cyber-Kokoro en línea! Sistemas al 100%, sincronizando latidos y música futurista."
  },
  {
    id: "traditional_kimono",
    name: "Kimono de Seda",
    category: "Tradicional",
    isVip: true,
    emoji: "👘",
    accent: "#F59E0B",
    image: "kokoro_outfit_kimono.png",
    hairstyle: "Peinado Shimada Ceremonial Imperial",
    accessory: "👘 Peineta dorada & Faja Obi de oro",
    desc: "Kimono rojo escarlata y púrpura con bordados de grullas doradas en relieve, mangas largas furisode y faja brocada.",
    quote: "Es un gran honor lucir este kimono ceremonial ante ti. Que la armonía y la paz acompañen tu camino."
  },
  {
    id: "sporty_fitness",
    name: "Ropa Deportiva",
    category: "Deporte",
    isVip: false,
    emoji: "👟",
    accent: "#10B981",
    image: "kokoro_outfit_sporty.png",
    hairstyle: "Cola de Caballo Deportiva con Cinta",
    accessory: "👟 Cinta deportiva & Muñequeras",
    desc: "Top deportivo transpirable violeta, chaqueta corta cortaviento blanca, calzas deportivas con franjas reflectivas.",
    quote: "¡Vamos Matias, a mover el cuerpo! Una sesión de estiramiento y a seguir programando con energía."
  }
];

const VOICES = [
  { id: "hana", name: "Hana (Suave - Gratis)", tone: "Tono dulce y reconfortante waifu", isVip: false, sample: "¡Hola Matias! Estoy aquí para acompañarte y cuidarte hoy.", pitchMultiplier: 1.15, rateMultiplier: 0.95 },
  { id: "aoi", name: "Aoi (Neutra - Gratis)", tone: "Voz equilibrada y clara de lectura", isVip: false, sample: "Tu agenda y recordatorios están listos para la sesión.", pitchMultiplier: 1.0, rateMultiplier: 1.0 },
  { id: "luz", name: "Luz (Alegre - Gratis)", tone: "Entonación entusiasta, brillante y vivaz", isVip: false, sample: "¡Vamos con toda la energía hoy, eres el mejor!", pitchMultiplier: 1.25, rateMultiplier: 1.10 },
  { id: "elena", name: "Elena (Serena - Gratis)", tone: "Voz pausada y relajante para estudiar", isVip: false, sample: "Respira hondo y concéntrate, lo estás haciendo excelente.", pitchMultiplier: 0.88, rateMultiplier: 0.90 },
  { id: "yuki", name: "Yuki (Enérgica - VIP)", tone: "Animada con matices kawaii japoneses", isVip: true, sample: "¡Yay! ¡Kokoro Plus te da energía infinita!", pitchMultiplier: 1.30, rateMultiplier: 1.05 },
  { id: "rei", name: "Rei (Madura - VIP)", tone: "Voz profunda, elegante y de timbre sedoso", isVip: true, sample: "Permíteme encargarme de todos los detalles por ti.", pitchMultiplier: 0.90, rateMultiplier: 0.95 }
];

// ================= APP STATE =================
let state = {
  currentOutfit: OUTFITS[0],
  isVipActive: false,
  affectLevel: 0.5,
  selectedVoiceId: "hana",
  pitch: 1.0,
  speed: 1.0,
  isPlaying: false,
  isStudyMode: false,
  currentTrackIndex: 0
};

// ================= AUDIO ENGINE (REAL AUDIO) =================
const audio = document.getElementById("lofi-audio");
const playBtn = document.getElementById("play-pause-btn");
const eq = document.getElementById("equalizer");
const slider = document.getElementById("audio-slider");

function togglePlayPause() {
  if (state.isPlaying) {
    audio.pause();
    state.isPlaying = false;
    playBtn.textContent = "▶";
    eq.classList.remove("playing");
    logEvent("AUDIO", "Música pausada");
  } else {
    audio.play().then(() => {
      state.isPlaying = true;
      playBtn.textContent = "⏸";
      eq.classList.add("playing");
      logEvent("AUDIO", "Reproduciendo pista Lo-Fi real: kokoro_lofi.mp3");
    }).catch(err => {
      logEvent("AUDIO", "Interacción requerida por el navegador para reproducir: " + err);
    });
  }
}

audio.addEventListener("timeupdate", () => {
  if (audio.duration) {
    const pct = (audio.currentTime / audio.duration) * 100;
    slider.value = pct;
  }
});

function seekAudio(val) {
  if (audio.duration) {
    audio.currentTime = (val / 100) * audio.duration;
    logEvent("AUDIO", `Seek al ${(val)}%`);
  }
}

function nextTrack() {
  document.getElementById("track-title").textContent = "Midnight Sakura Lofi";
  audio.currentTime = 0;
  if (!state.isPlaying) togglePlayPause();
  logEvent("AUDIO", "Siguiente pista: Midnight Sakura Lofi");
}

function prevTrack() {
  document.getElementById("track-title").textContent = "Tokyo Rain Lo-Fi";
  audio.currentTime = 0;
  if (!state.isPlaying) togglePlayPause();
  logEvent("AUDIO", "Pista anterior: Tokyo Rain Lo-Fi");
}

// ================= TTS ENGINE (WEB SPEECH API) =================
function speakText(text) {
  if (!('speechSynthesis' in window)) {
    logEvent("TTS", "SpeechSynthesis no soportado en este navegador.");
    return;
  }

  window.speechSynthesis.cancel(); // Detener locución previa

  const utterance = new SpeechSynthesisUtterance(text);
  utterance.lang = "es-ES";

  const voiceObj = VOICES.find(v => v.id === state.selectedVoiceId) || VOICES[0];
  const finalPitch = Math.min(2.0, Math.max(0.5, state.pitch * voiceObj.pitchMultiplier));
  const finalRate = Math.min(2.0, Math.max(0.5, state.speed * voiceObj.rateMultiplier));

  utterance.pitch = finalPitch;
  utterance.rate = finalRate;

  // Intenta encontrar una voz en español del sistema
  const sysVoices = window.speechSynthesis.getVoices();
  const esVoice = sysVoices.find(v => v.lang.startsWith("es") && (v.name.includes("Natural") || v.name.includes("Female") || v.name.includes("Helena") || v.name.includes("Sabina") || true));
  if (esVoice) utterance.voice = esVoice;

  utterance.onstart = () => {
    logEvent("TTS", `Kokoro hablando con voz '${voiceObj.name}': "${text.substring(0, 40)}..." (Pitch: ${finalPitch.toFixed(2)}, Rate: ${finalRate.toFixed(2)})`);
  };

  window.speechSynthesis.speak(utterance);
}

function speakCurrentGreeting() {
  const g = document.getElementById("dialogue-greeting").textContent;
  const s = document.getElementById("dialogue-sub").textContent;
  speakText(`${g} ${s}`);
}

function speakCurrentReaction() {
  const text = document.getElementById("live-reaction-text").textContent.replace(/"/g, '');
  speakText(text);
}

function speakModalQuote() {
  const quote = document.getElementById("modal-quote-text").textContent.replace(/"/g, '');
  speakText(quote);
}

// ================= ZERO-UI ACTIONS =================
function orderRamen() {
  logEvent("ACTION", "Zero-UI: Pedido de Ramen enviado a PedidosYa!");
  speakText("¡Entendido! Ya pedí tu ramen favorito con huevo y naruto en PedidosYa. ¡Llegará en breve!");
}

function toggleStudyMode() {
  state.isStudyMode = !state.isStudyMode;
  const pill = document.getElementById("study-pill");
  if (state.isStudyMode) {
    pill.classList.add("active");
    pill.innerHTML = "<span>📖</span> Studying...";
    if (!state.isPlaying) togglePlayPause();
    logEvent("ACTION", "Study Mode: Temporizador Pomodoro 25m activado con música Lo-Fi.");
    speakText("Modo estudio activado. Pongamos algo de música Lo-Fi relajante. ¡A concentrarse!");
  } else {
    pill.classList.remove("active");
    pill.innerHTML = "<span>📖</span> Study Mode";
    logEvent("ACTION", "Study Mode desactivado.");
    speakText("Sesión de estudio terminada. ¡Hiciste un gran trabajo!");
  }
}

// ================= PERSONALITY SLIDER & MODES =================
function onAffectChange(val) {
  const num = val / 100;
  state.affectLevel = num;

  const badge = document.getElementById("personality-badge");
  const greeting = document.getElementById("dialogue-greeting");
  const sub = document.getElementById("dialogue-sub");
  const reaction = document.getElementById("live-reaction-text");

  if (num < 0.35) {
    badge.textContent = "傲娇 Tsundere";
    greeting.textContent = "¡Buenas noches Matias! ...No es que me importe, pero ya es hora de cenar.";
    sub.textContent = "¿Quieres tu ramen de PedidosYa o piensas seguir trabajando sin comer? ¡B-baka!";
    reaction.textContent = '"¡B-baka! No es que me preocupe por ti o algo así... pero toma un poco de agua y descansa un momento, ¿sí?"';
    logEvent("STATE", "Personalidad cambiada a: Tsundere (Nivel: " + num.toFixed(2) + ")");
  } else if (num <= 0.75) {
    badge.textContent = "甘えん坊 Dulce & Waifu";
    greeting.textContent = "Buenas noches Matias, ¿te pido tu ramen favorito en PedidosYa?";
    sub.textContent = "Noté que llevas 2 horas trabajando. Te traje té verde para concentrarte. ¿También querés que pida tu cena habitual?";
    reaction.textContent = '"¡Te extrañé mucho Matias! Siempre estoy aquí para apoyarte y cuidarte. ¡Haces un gran trabajo hoy! ♡"';
    logEvent("STATE", "Personalidad cambiada a: Dulce & Waifu (Nivel: " + num.toFixed(2) + ")");
  } else {
    badge.textContent = "秘書 Profesional";
    greeting.textContent = "Buenas noches Matías. Reporte: 2 horas continuas de productividad registradas.";
    sub.textContent = "Sugerencia de descanso: Solicitud habitual de ramen en PedidosYa lista para despachar.";
    reaction.textContent = '"Buenas tardes Matías. Tu agenda se encuentra optimizada y las prioridades de trabajo están listas. ¿Deseas iniciar la sesión?"';
    logEvent("STATE", "Personalidad cambiada a: Profesional (Nivel: " + num.toFixed(2) + ")");
  }
}

function onPitchChange(val) {
  state.pitch = val / 100;
  document.getElementById("pitch-val").textContent = state.pitch.toFixed(1) + "x";
  logEvent("STATE", "Pitch ajustado a: " + state.pitch.toFixed(1) + "x");
}

function onSpeedChange(val) {
  state.speed = val / 100;
  document.getElementById("speed-val").textContent = state.speed.toFixed(2) + "x";
  logEvent("STATE", "Velocidad ajustada a: " + state.speed.toFixed(2) + "x");
}

// ================= WARDROBE & OUTFIT SELECTOR =================
function renderOutfits() {
  const container = document.getElementById("outfit-grid-container");
  container.innerHTML = "";

  OUTFITS.forEach(outfit => {
    const isEquipped = outfit.id === state.currentOutfit.id;
    const isLocked = outfit.isVip && !state.isVipActive;

    const card = document.createElement("div");
    card.className = `outfit-card ${isEquipped ? 'equipped' : ''}`;
    card.onclick = () => selectOutfit(outfit);

    let badgeHtml = "";
    if (isEquipped) badgeHtml = `<span class="outfit-badge badge-equipped">Equipado</span>`;
    else if (outfit.isVip) badgeHtml = `<span class="outfit-badge badge-vip">${isLocked ? '🔒 ' : ''}VIP</span>`;
    else badgeHtml = `<span class="outfit-badge badge-free">Gratis</span>`;

    card.innerHTML = `
      <div style="display:flex; justify-content:space-between; align-items:center;">
        <span style="font-size:10px; color:var(--text-hint);">${outfit.category}</span>
        ${badgeHtml}
      </div>
      <div style="font-size:36px; margin: 8px 0;">${outfit.emoji}</div>
      <div style="font-size:12px; font-weight:bold; color:var(--text-primary);">${outfit.name}</div>
      <div style="font-size:10px; color:var(--text-secondary); margin-top:2px;">${outfit.hairstyle}</div>
    `;

    container.appendChild(card);
  });
}

function renderModalOutfitSelector() {
  const container = document.getElementById("modal-outfit-selector");
  container.innerHTML = "";

  OUTFITS.forEach(outfit => {
    const isSelected = outfit.id === state.currentOutfit.id;
    const item = document.createElement("div");
    item.style.cssText = `
      min-width: 44px; height: 44px; border-radius: 12px; display: flex; align-items: center; justify-content: center;
      font-size: 20px; cursor: pointer; border: 2px solid ${isSelected ? outfit.accent : 'rgba(0,0,0,0.1)'};
      background: ${isSelected ? outfit.accent : '#FFF'}; color: ${isSelected ? '#FFF' : '#000'}; box-shadow: 0 2px 8px rgba(0,0,0,0.08);
    `;
    item.textContent = outfit.emoji;
    item.title = outfit.name;
    item.onclick = () => selectOutfit(outfit);
    container.appendChild(item);
  });
}

function selectOutfit(outfit) {
  if (outfit.isVip && !state.isVipActive) {
    logEvent("ACTION", `Atuendo VIP '${outfit.name}' bloqueado. Requiere suscripción.`);
    alert(`✨ '${outfit.name}' es un atuendo exclusivo de Kokoro Plus VIP. ¡Activa tu prueba gratis de 7 días!`);
    return;
  }

  state.currentOutfit = outfit;
  document.getElementById("equipped-emoji").textContent = outfit.emoji;
  document.getElementById("equipped-name").textContent = outfit.name;
  document.getElementById("avatar-sticker").textContent = outfit.emoji;
  document.getElementById("avatar-outfit-tag").style.borderColor = outfit.accent;
  document.getElementById("avatar-aura").style.background = `radial-gradient(circle, ${outfit.accent}66 0%, rgba(236, 72, 153, 0.2) 50%, transparent 70%)`;

  // Update Main Avatar and Full-Body Modal Images
  if (outfit.image) {
    const mainImg = document.getElementById("avatar-main-img");
    if (mainImg) mainImg.src = outfit.image;
    const modalImg = document.getElementById("modal-fullbody-img");
    if (modalImg) modalImg.src = outfit.image;
  }

  // Update Full-body modal view
  document.getElementById("modal-emoji-badge").textContent = outfit.emoji;
  document.getElementById("modal-outfit-badge").textContent = `${outfit.emoji} ${outfit.name}`;
  document.getElementById("modal-outfit-badge").style.background = outfit.accent;
  document.getElementById("modal-hair-chip").textContent = `💇 ${outfit.hairstyle}`;
  document.getElementById("modal-acc-chip").textContent = outfit.accessory;
  document.getElementById("modal-fullbody-desc").textContent = outfit.desc;
  document.getElementById("modal-quote-text").textContent = `"${outfit.quote}"`;

  renderOutfits();
  renderModalOutfitSelector();

  logEvent("STATE", `Atuendo equipado: ${outfit.name} | Peinado: ${outfit.hairstyle} | Accesorio: ${outfit.accessory}`);
  speakText(outfit.quote);
}

// ================= VIP SUBSCRIPTION =================
function activateVipTrial() {
  state.isVipActive = true;
  document.getElementById("vip-badge-tag").style.display = "inline-block";
  document.getElementById("vip-trial-btn").style.display = "none";
  document.getElementById("vip-status-text").textContent = "Kokoro Plus VIP ✨";
  document.getElementById("vip-status-text").style.color = "var(--vip-gold)";

  renderOutfits();
  renderVoices();
  logEvent("ACTION", "✨ Kokoro Plus VIP Activado! Prueba gratis de 7 días iniciada.");
  speakText("¡Felicidades Matias! Ahora tienes acceso VIP ilimitado a todos mis trajes y peinados.");
}

function toggleVipSubscription() {
  if (state.isVipActive) {
    state.isVipActive = false;
    document.getElementById("vip-badge-tag").style.display = "none";
    document.getElementById("vip-trial-btn").style.display = "block";
    document.getElementById("vip-status-text").textContent = "Modo Compañera Activa";
    document.getElementById("vip-status-text").style.color = "var(--primary-violet)";
    logEvent("STATE", "VIP Subscription desactivada.");
  } else {
    activateVipTrial();
  }
}

// ================= VOICES SELECTOR =================
function renderVoices() {
  const container = document.getElementById("voices-list-container");
  container.innerHTML = "";

  VOICES.forEach(voice => {
    const isSelected = voice.id === state.selectedVoiceId;
    const isLocked = voice.isVip && !state.isVipActive;

    const row = document.createElement("div");
    row.className = `voice-row ${isSelected ? 'selected' : ''}`;
    row.onclick = () => selectVoice(voice);

    row.innerHTML = `
      <div style="display:flex; align-items:center; gap:10px;">
        <div style="width:18px; height:18px; border-radius:50%; border:2px solid var(--primary-violet); display:flex; align-items:center; justify-content:center;">
          ${isSelected ? '<div style="width:8px; height:8px; border-radius:50%; background:var(--primary-violet);"></div>' : ''}
        </div>
        <div>
          <div style="font-size:13px; font-weight:bold; color:var(--text-primary);">
            ${voice.name}
            ${voice.isVip ? `<span class="outfit-badge badge-vip">${isLocked ? '🔒 ' : ''}VIP</span>` : ''}
          </div>
          <div style="font-size:11px; color:var(--text-secondary);">${voice.tone}</div>
        </div>
      </div>
      <button class="speak-btn" onclick="event.stopPropagation(); speakText('${voice.sample}')" title="Escuchar muestra">🔊</button>
    `;

    container.appendChild(row);
  });
}

function selectVoice(voice) {
  if (voice.isVip && !state.isVipActive) {
    logEvent("ACTION", `Voz VIP '${voice.name}' bloqueada. Requiere suscripción.`);
    alert(`✨ '${voice.name}' es una voz neuronal exclusiva de Kokoro Plus VIP.`);
    return;
  }
  state.selectedVoiceId = voice.id;
  renderVoices();
  logEvent("STATE", `Voz de lectura cambiada a: ${voice.name}`);
  speakText(voice.sample);
}

// ================= MODAL FULL BODY =================
function openFullBodyModal() {
  document.getElementById("fullbody-modal").classList.add("active");
  renderModalOutfitSelector();
  logEvent("ACTION", `Visualizador de cuerpo completo abierto con atuendo: ${state.currentOutfit.name}`);
}

function closeFullBodyModal() {
  document.getElementById("fullbody-modal").classList.remove("active");
}

// ================= TABS SWITCHING & SWIPE =================
const TAB_ORDER = ["home", "wardrobe", "personality"];

function switchTab(tabId) {
  document.querySelectorAll(".screen").forEach(s => s.classList.remove("active"));
  document.querySelectorAll(".nav-item").forEach(b => b.classList.remove("active"));

  const targetScreen = document.getElementById(`screen-${tabId}`);
  const targetNavBtn = document.getElementById(`nav-btn-${tabId}`);
  if (targetScreen) targetScreen.classList.add("active");
  if (targetNavBtn) targetNavBtn.classList.add("active");

  logEvent("STATE", `Navegación: Pantalla ${tabId.toUpperCase()}`);
}

// Swipe detection for web preview
let touchStartX = 0;
let touchEndX = 0;

document.addEventListener("touchstart", e => {
  touchStartX = e.changedTouches[0].screenX;
}, { passive: true });

document.addEventListener("touchend", e => {
  touchEndX = e.changedTouches[0].screenX;
  handleGesture();
}, { passive: true });

// Mouse drag simulation for desktop browser
let mouseStartX = 0;
let isMouseDown = false;

document.addEventListener("mousedown", e => {
  // Ignore clicks on buttons/inputs
  if (["INPUT", "BUTTON", "A"].includes(e.target.tagName)) return;
  mouseStartX = e.clientX;
  isMouseDown = true;
});

document.addEventListener("mouseup", e => {
  if (!isMouseDown) return;
  isMouseDown = false;
  const diffX = e.clientX - mouseStartX;
  if (Math.abs(diffX) > 70) {
    navigateSwipe(diffX < 0 ? 1 : -1);
  }
});

function handleGesture() {
  const diffX = touchEndX - touchStartX;
  if (Math.abs(diffX) > 60) {
    navigateSwipe(diffX < 0 ? 1 : -1);
  }
}

function navigateSwipe(direction) {
  const currentActive = document.querySelector(".screen.active");
  if (!currentActive) return;
  const currentId = currentActive.id.replace("screen-", "");
  const currentIndex = TAB_ORDER.indexOf(currentId);
  if (currentIndex === -1) return;

  const nextIndex = currentIndex + direction;
  if (nextIndex >= 0 && nextIndex < TAB_ORDER.length) {
    switchTab(TAB_ORDER[nextIndex]);
  }
}

// ================= DEBUG CONSOLE LOGGING =================
function logEvent(type, message) {
  const logView = document.getElementById("debug-log-view");
  const time = new Date().toLocaleTimeString();
  const line = document.createElement("div");
  line.className = `log-line ${type.toLowerCase()}`;
  line.textContent = `[${time}] [${type}] ${message}`;
  logView.appendChild(line);
  logView.scrollTop = logView.scrollHeight;
}

function clearLogs() {
  document.getElementById("debug-log-view").innerHTML = "";
  logEvent("SISTEMA", "Logs reiniciados.");
}

// ================= BACKEND API INTEGRATION =================
const BACKEND_BASE_URL = "https://k9br9dc7-5123.brs.devtunnels.ms";
const BACKEND_HEADERS = {
  "X-Tunnel-Skip-Anti-Abuse-Page": "true",
  "Content-Type": "application/json"
};

async function sendWebChatMessage() {
  const input = document.getElementById("chat-input");
  const sendBtn = document.getElementById("chat-send-btn");
  const msg = input.value.trim();
  if (!msg) return;

  input.value = "";
  input.disabled = true;
  sendBtn.innerHTML = "⏳";
  logEvent("CHAT", `Usuario: "${msg}"`);

  try {
    const res = await fetch(`${BACKEND_BASE_URL}/api/Chat`, {
      method: "POST",
      headers: BACKEND_HEADERS,
      body: JSON.stringify({ message: msg })
    });

    if (res.ok) {
      const data = await res.json();
      document.getElementById("dialogue-greeting").textContent = data.reply;
      document.getElementById("dialogue-sub").textContent = `Kokoro AI • Modo: ${data.personalityMode || 'Compañera'}`;
      logEvent("AI", `Kokoro (${data.personalityMode}): "${data.reply}"`);
      speakText(data.reply);
    } else {
      logEvent("ERROR", `Error al comunicarse con Gemini AI: HTTP ${res.status}`);
    }
  } catch (err) {
    logEvent("ERROR", `Error de conexión con el backend: ${err.message}`);
  } finally {
    input.disabled = false;
    sendBtn.innerHTML = "➤";
    input.focus();
  }
}

async function syncBackendData() {
  try {
    const res = await fetch(`${BACKEND_BASE_URL}/api/Dashboard`, { headers: BACKEND_HEADERS });
    if (res.ok) {
      const data = await res.json();
      if (data.isVip) {
        state.isVipActive = true;
        document.getElementById("vip-badge-tag").style.display = "inline-block";
        document.getElementById("vip-trial-btn").style.display = "none";
        document.getElementById("vip-status-text").textContent = "Kokoro Plus VIP ✨";
        document.getElementById("vip-status-text").style.color = "var(--vip-gold)";
        renderOutfits();
        renderVoices();
      }
      if (data.proactiveDialogue) {
        document.getElementById("dialogue-greeting").textContent = data.proactiveDialogue.message;
        document.getElementById("dialogue-sub").textContent = data.proactiveDialogue.subtext;
      }
      logEvent("BACKEND", "Sincronizado con ASP.NET Core & PostgreSQL exitosamente.");
    }
  } catch (e) {
    logEvent("BACKEND", "Modo offline (backend local en espera).");
  }
}

// ================= INIT =================
window.addEventListener("DOMContentLoaded", () => {
  renderOutfits();
  renderVoices();
  renderModalOutfitSelector();
  syncBackendData();
  logEvent("SISTEMA", "Todos los componentes inicializados correctamente.");
});
