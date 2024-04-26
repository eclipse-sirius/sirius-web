/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import { expect, test } from 'vitest';
import { applyCompletionProposal } from '../TextfieldPropertySection';
import { GQLCompletionProposal, TextFieldState } from '../TextfieldPropertySection.types';

test('apply empty proposal anywhere', () => {
  const text = 'some text';
  for (let i = 0; i < text.length; i++) {
    const initial: TextFieldState = {
      textValue: 'some text',
      cursorPosition: i,
    };
    const proposal: GQLCompletionProposal = {
      description: 'test proposal',
      textToInsert: '',
      charsToReplace: 0,
    };
    const result = applyCompletionProposal(initial, proposal);
    expect(result).toStrictEqual(initial);
  }
});

test('apply full proposal at the end', () => {
  const initial: TextFieldState = {
    textValue: 'text',
    cursorPosition: 'text'.length,
  };
  const proposal: GQLCompletionProposal = {
    description: 'test proposal',
    textToInsert: 'suffix',
    charsToReplace: 0,
  };
  const result = applyCompletionProposal(initial, proposal);
  expect(result).toStrictEqual({
    textValue: 'textsuffix',
    cursorPosition: 'textsuffix'.length,
  });
});

test('apply proposal with common prefix at the end', () => {
  const initial: TextFieldState = {
    textValue: 'fooB',
    cursorPosition: 4,
  };
  const proposal: GQLCompletionProposal = {
    description: 'test proposal',
    textToInsert: 'fooBar',
    charsToReplace: 4,
  };
  const result = applyCompletionProposal(initial, proposal);
  expect(result).toStrictEqual({
    textValue: 'fooBar',
    cursorPosition: 'fooBar'.length,
  });
});

test('apply proposal with common prefix at the end', () => {
  const initial: TextFieldState = {
    textValue: 'aql:fooB',
    cursorPosition: 'aql:fooB'.length,
  };
  const proposal: GQLCompletionProposal = {
    description: 'test proposal',
    textToInsert: 'fooBar',
    charsToReplace: 4,
  };
  const result = applyCompletionProposal(initial, proposal);
  expect(result).toStrictEqual({
    textValue: 'aql:fooBar',
    cursorPosition: 'aql:fooBar'.length,
  });
});

test('apply proposal in the middle', () => {
  const initial: TextFieldState = {
    textValue: 'aql:self',
    cursorPosition: 'aql:s'.length,
  };
  const proposal: GQLCompletionProposal = {
    description: 'test proposal',
    textToInsert: 'self',
    charsToReplace: 1,
  };
  const result = applyCompletionProposal(initial, proposal);
  expect(result).toStrictEqual({
    textValue: 'aql:selfelf',
    cursorPosition: 'aql:self'.length,
  });
});

test('complete domain type', () => {
  const initial: TextFieldState = {
    textValue: 'flow::FlowTarget',
    cursorPosition: 'flow::Flow'.length,
  };
  const proposal: GQLCompletionProposal = {
    description: 'test proposal',
    textToInsert: 'flow::FlowSource',
    charsToReplace: 'flow::Flow'.length,
  };
  const result = applyCompletionProposal(initial, proposal);
  expect(result).toStrictEqual({
    textValue: 'flow::FlowSourceTarget',
    cursorPosition: 'flow::FlowSource'.length,
  });
});
