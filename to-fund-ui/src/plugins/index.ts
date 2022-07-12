import type { App } from 'vue';
import setupAssets from './assets';
import setupVxeTable from './vue-table';

export default function setupPlugin(app: App) {
	app.use(setupVxeTable);
}

export { setupAssets, setupPlugin };
