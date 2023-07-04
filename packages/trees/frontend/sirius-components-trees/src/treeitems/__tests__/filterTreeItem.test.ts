/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { splitText } from '../filterTreeItem';

test('should split text in a case insensitive manner', () => {
  const comPattern = 'com';
  const ompPattern = 'omp';
  const scPattern = '[]*(){}^/$';

  expect(splitText('Composed Composite Computer ComCom - com2', comPattern)).toStrictEqual([
    'Com',
    'posed ',
    'Com',
    'posite ',
    'Com',
    'puter ',
    'Com',
    '',
    'Com',
    ' - ',
    'com',
    '2',
  ]);
  expect(splitText('cOmposed COMposite coMputer cOmCoM - com2', comPattern)).toStrictEqual([
    'cOm',
    'posed ',
    'COM',
    'posite ',
    'coM',
    'puter ',
    'cOm',
    '',
    'CoM',
    ' - ',
    'com',
    '2',
  ]);
  expect(splitText('COMPOSED COMPOSITE COMPUTER COMCOM - COM', comPattern)).toStrictEqual([
    'COM',
    'POSED ',
    'COM',
    'POSITE ',
    'COM',
    'PUTER ',
    'COM',
    '',
    'COM',
    ' - ',
    'COM',
  ]);
  expect(splitText('ComCOMcom', comPattern)).toStrictEqual(['Com', '', 'COM', '', 'com']);
  expect(splitText('Composed Composite Computer ompomp - comp2', ompPattern)).toStrictEqual([
    'C',
    'omp',
    'osed C',
    'omp',
    'osite C',
    'omp',
    'uter ',
    'omp',
    '',
    'omp',
    ' - c',
    'omp',
    '2',
  ]);
  expect(splitText('cOmposed COMposite coMputer oMpOmP - comp2', ompPattern)).toStrictEqual([
    'c',
    'Omp',
    'osed C',
    'OMp',
    'osite c',
    'oMp',
    'uter ',
    'oMp',
    '',
    'OmP',
    ' - c',
    'omp',
    '2',
  ]);
  expect(splitText('COMPOSED COMPOSITE COMPUTER OMPOMP - COMP', ompPattern)).toStrictEqual([
    'C',
    'OMP',
    'OSED C',
    'OMP',
    'OSITE C',
    'OMP',
    'UTER ',
    'OMP',
    '',
    'OMP',
    ' - C',
    'OMP',
  ]);

  expect(splitText('Something Different', comPattern)).toStrictEqual(['Something Different']);
  expect(splitText('Another Label', comPattern)).toStrictEqual(['Another Label']);
  expect(splitText('A null pattern', null)).toStrictEqual(['A null pattern']);
  expect(splitText('An undefined pattern', undefined)).toStrictEqual(['An undefined pattern']);
  expect(splitText('An empty pattern', '')).toStrictEqual(['An empty pattern']);
  expect(splitText('A pattern with []*(){}^/$ special characters', scPattern)).toStrictEqual([
    'A pattern with ',
    '[]*(){}^/$',
    ' special characters',
  ]);
});
