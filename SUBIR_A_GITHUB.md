# Cómo subir Física Lab a GitHub (sin usar la terminal)

Esta guía te permite publicar el proyecto completo usando solo el navegador
web, sin instalar Git ni usar la línea de comandos.

## 1. Crear el repositorio

1. Entra a [github.com](https://github.com) e inicia sesión.
2. Haz clic en el botón **"+"** (arriba a la derecha) → **"New repository"**.
3. En **Repository name** escribe: `physics-lab-kids-android`
4. Déjalo como **Public** (o **Private**, como prefieras).
5. **No marques** "Add a README file" (ya tenemos uno en el proyecto).
6. Haz clic en **"Create repository"**.

## 2. Subir todos los archivos

1. En la página del repositorio recién creado, haz clic en el enlace
   **"uploading an existing file"** (o ve a la pestaña **Code** →
   **Add file** → **Upload files**).
2. Descomprime el archivo `.zip` de Física Lab en tu computadora.
3. Arrastra **todo el contenido** de la carpeta descomprimida (no la carpeta
   en sí, sino lo que está adentro: `app/`, `.github/`, `database/`,
   `README.md`, `gradlew`, etc.) a la zona de carga de GitHub.

   > 💡 GitHub permite arrastrar carpetas completas y respeta la
   > estructura de subcarpetas automáticamente.

4. Baja hasta el final de la página, escribe un mensaje de commit como
   `"Primera versión de Física Lab"` y haz clic en **"Commit changes"**.

Si el navegador te limita la cantidad de archivos por carga (puede pasar
con proyectos grandes), sube el proyecto en varias tandas: primero la
carpeta `app/`, luego `.github/`, luego el resto de archivos sueltos y
`database/`. GitHub va combinando todo en el mismo repositorio.

## 3. Verificar que las Actions se ejecuten

1. Ve a la pestaña **Actions** de tu repositorio.
2. Deberías ver el workflow **"android-build"** ejecutándose automáticamente
   después de tu primer commit (compila la app y corre las pruebas).
3. Si hiciste cambios en `MEMORIA_DESCRIPTIVA.md`, `MANUAL_USUARIO.md` o
   `MANUAL_TECNICO.md`, también se ejecutará **"docs-build"**, que genera los
   PDFs de documentación.
4. Haz clic en la ejecución más reciente para ver el detalle de cada paso.

### Si algún paso sale en rojo (❌)

1. Haz clic en el paso que falló para ver el mensaje de error completo.
2. Copia el mensaje de error y compártelo en la conversación con Claude
   (o con quien te esté ayudando) para corregirlo.
3. Corrige el archivo indicado, vuelve a subirlo desde **Add file → Upload
   files** (esto crea un nuevo commit), y las Actions se ejecutarán de
   nuevo automáticamente.
4. Repite hasta que **android-build** y **docs-build** aparezcan en verde ✅.

## 4. Publicar la versión 1.0.0 (Release)

Hay dos formas de disparar el workflow **release.yml**:

### Opción A — Crear un tag desde la interfaz web

1. Ve a la pestaña **Code** de tu repositorio.
2. Haz clic en el selector de ramas/tags (donde dice "main") → pestaña
   **"Tags"** → **"Create a new tag"** (si no aparece la opción, puedes
   crear el tag desde **Releases**, ver Opción B).

### Opción B — Crear un Release directamente (recomendada)

1. Ve a la pestaña **Releases** (o `github.com/tu-usuario/physics-lab-kids-android/releases`).
2. Haz clic en **"Create a new release"** o **"Draft a new release"**.
3. En **"Choose a tag"**, escribe `v1.0.0` y selecciona **"Create new tag on
   publish"**.
4. En **"Release title"** escribe: `Física Lab v1.0.0`
5. Haz clic en **"Publish release"**.

Esto dispara el workflow `release.yml`, que compila el APK y adjunta el
archivo `FisicaLab-v1.0.0.apk` automáticamente al Release en unos minutos.
Actualiza la página del Release para ver el archivo adjunto una vez que la
Action termine (revisa la pestaña **Actions** si tarda demasiado o falla).

## 5. Descargar e instalar el APK

1. Ve a la pestaña **Releases**.
2. Abre **"Física Lab v1.0.0"**.
3. Descarga `FisicaLab-v1.0.0.apk` en tu teléfono Android.
4. Abre el archivo descargado y permite la instalación desde "orígenes
   desconocidos" si el sistema lo solicita (esto es normal para apps que no
   vienen de Google Play).

¡Listo! Física Lab queda instalada y funcionando 100% offline. 🎉
