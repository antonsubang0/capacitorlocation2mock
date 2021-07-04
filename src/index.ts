import { registerPlugin } from '@capacitor/core';

import type { MockLocationPlugin } from './definitions';

const MockLocation = registerPlugin<MockLocationPlugin>('MockLocation', {
  web: () => import('./web').then(m => new m.MockLocationWeb()),
});

export * from './definitions';
export { MockLocation };
