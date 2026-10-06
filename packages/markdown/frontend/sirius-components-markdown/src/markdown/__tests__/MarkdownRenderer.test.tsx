/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { act, cleanup, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { $createParagraphNode, $createTextNode, $getRoot, LexicalEditor } from 'lexical';
import { afterEach, expect, test, vi } from 'vitest';
import { MarkdownRenderer } from '../MarkdownRenderer';

// jsdom 16 cannot evaluate :focus-within; these tests exercise editor behavior without tss styles.
vi.mock('tss-react/mui', async (importOriginal) => ({
  ...(await importOriginal<typeof import('tss-react/mui')>()),
  makeStyles: () => () => () => ({ classes: new Proxy({}, { get: (_, name) => String(name) }) }),
}));

afterEach(cleanup);

const props = { placeholder: 'Description', readOnly: false, onBlur: vi.fn() };
const textbox = () => screen.getByRole('textbox', { hidden: true });
const toggle = () => screen.getByRole('button', { name: 'Plain text', hidden: true });
const editor = () => (textbox() as HTMLElement & { __lexicalEditor: LexicalEditor }).__lexicalEditor;
const expectSource = async (value: string) => waitFor(() => expect(textbox().textContent).toBe(value));
const replaceText = async (value: string) => {
  await act(async () => {
    editor().update(
      () =>
        $getRoot()
          .clear()
          .append($createParagraphNode().append($createTextNode(value))),
      {
        discrete: true,
      }
    );
  });
};

test.each([
  '',
  '**bold** and _italic_  \n\n',
  '# Heading\n\n* first\n* second\n\n',
  '- [ ] todo\n- [x] done',
  '\tindented\n\n```js\nconst value = 1;\n```\n[link](https://example.org)',
])('switching without editing preserves the exact source: %j', async (value) => {
  const onBlur = vi.fn();
  render(<MarkdownRenderer {...props} value={value} onBlur={onBlur} />);
  fireEvent.focus(textbox());
  fireEvent.blur(textbox(), { relatedTarget: toggle() });
  for (let i = 0; i < 2; i++) {
    fireEvent.click(toggle());
    await expectSource(value);
    fireEvent.click(toggle());
  }
  expect(onBlur).not.toHaveBeenCalled();
  fireEvent.blur(toggle(), { relatedTarget: document.body });
  expect(onBlur).toHaveBeenLastCalledWith(value);
});

test('plain text keeps literal markers and unsaved edits survive both switches', async () => {
  const onBlur = vi.fn();
  render(<MarkdownRenderer {...props} value="original" plainTextByDefault onBlur={onBlur} />);
  const source = '**edited**\n\n- [ ] literal\n';
  await replaceText(source);
  await expectSource(source);
  expect(textbox().querySelector('strong, ul')).toBeNull();
  fireEvent.click(toggle());
  await waitFor(() => expect(textbox().querySelector('strong')?.textContent).toBe('edited'));
  fireEvent.click(toggle());
  await expectSource(source);
  expect(onBlur).not.toHaveBeenCalled();
  fireEvent.blur(toggle(), { relatedTarget: document.body });
  expect(onBlur).toHaveBeenLastCalledWith(source);
});

test('rendered edits are serialized before switching to source mode', async () => {
  render(<MarkdownRenderer {...props} value="old" />);
  await replaceText('new value');
  fireEvent.click(toggle());
  await expectSource('new value');
});

test('refresh keeps the local mode while replacement applies the configured default', async () => {
  const { rerender } = render(<MarkdownRenderer key="first" {...props} value="old" />);
  fireEvent.click(toggle());
  rerender(<MarkdownRenderer key="first" {...props} value="**refreshed**" />);
  await expectSource('**refreshed**');
  expect(toggle().getAttribute('aria-pressed')).toBe('true');
  rerender(<MarkdownRenderer key="second" {...props} value="**refreshed**" />);
  await waitFor(() => expect(textbox().querySelector('strong')?.textContent).toBe('refreshed'));
  expect(toggle().getAttribute('aria-pressed')).toBe('false');
});

test('read-only widgets switch modes without editing or submitting', async () => {
  const onBlur = vi.fn();
  render(<MarkdownRenderer {...props} value="**source**" readOnly onBlur={onBlur} />);
  expect(editor().isEditable()).toBe(false);
  expect(screen.queryByRole('button', { name: 'Insert Link', hidden: true })).toBeNull();
  fireEvent.click(toggle());
  await expectSource('**source**');
  fireEvent.blur(toggle(), { relatedTarget: document.body });
  expect(onBlur).not.toHaveBeenCalled();
});
