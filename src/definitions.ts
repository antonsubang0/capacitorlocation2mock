export interface MockLocationPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
