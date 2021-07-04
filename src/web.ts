import { WebPlugin } from '@capacitor/core';

import type { MockLocationPlugin } from './definitions';

export class MockLocationWeb extends WebPlugin implements MockLocationPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
