import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import { nodePolyfills } from 'vite-plugin-node-polyfills'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [
    nodePolyfills(
      {
        protocolImports: true,
      }
    ),
    vue(),
  ],
  optimizeDeps: {
    force:true
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    }
  },
})
