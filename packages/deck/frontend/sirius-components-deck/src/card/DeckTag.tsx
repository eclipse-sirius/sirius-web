/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { TagSpan } from '../styled/DeckCardStyledComponents';
import { DeckTagProps } from './DeckTag.types';

export const DeckTag = ({ title, color, bgcolor, tagStyle }: DeckTagProps) => {
  const style = { color: color || 'white', backgroundColor: bgcolor || 'orange', ...tagStyle };
  return <TagSpan style={style}>{title}</TagSpan>;
};
