/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import Link from '@material-ui/core/Link';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { useEffect, useRef, useState } from 'react';
import {
  getTextDecorationLineValue,
  readBooleanStyleProperty,
  readNumericStyleProperty,
  readStyleProperty,
} from './styleProperties';
import { WidgetProps } from './WidgetEntry.types';

interface StyleProps {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}
const useStyle = makeStyles<Theme, StyleProps>((theme) => ({
  style: ({ color }) => {
    const style = {
      fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
      fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
      fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
      textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    };
    if (color) {
      style['color'] = color;
    }
    return style;
  },
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const LinkWidget = ({ widget, selection }: WidgetProps) => {
  const props: StyleProps = {
    color: readStyleProperty(widget, 'color'),
    fontSize: readNumericStyleProperty(widget, 'fontSize'),
    italic: readBooleanStyleProperty(widget, 'italic'),
    bold: readBooleanStyleProperty(widget, 'bold'),
    underline: readBooleanStyleProperty(widget, 'underline'),
    strikeThrough: readBooleanStyleProperty(widget, 'strikeThrough'),
  };

  const classes = useStyle(props);

  const [selected, setSelected] = useState<boolean>(false);

  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [selection, widget]);

  let className = classes.style;
  if (selected) {
    className = `${className} ${classes.selected}`;
  }
  return (
    <div>
      <Link
        ref={ref}
        className={className}
        onClick={(event) => {
          event.preventDefault();
          setSelected(true);
        }}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
        tabIndex={0}
        href="#"
        rel="noopener noreferrer"
        target="_blank">
        {widget.label}
      </Link>
    </div>
  );
};
