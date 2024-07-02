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
import { getCSSColor, useSelection } from '@eclipse-sirius/sirius-components-core';
import { LinkStyleProps, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import Link from '@mui/material/Link';
import { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { LinkWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<LinkStyleProps>()(
  (theme, { color, fontSize, italic, bold, underline, strikeThrough }) => ({
    style: {
      color: color ? getCSSColor(color, theme) : null,
      fontSize: fontSize ? fontSize : null,
      fontStyle: italic ? 'italic' : null,
      fontWeight: bold ? 'bold' : null,
      textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
    },
    selected: {
      color: theme.palette.primary.main,
    },
    propertySectionLabel: {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
    },
  })
);

export const LinkWidget = ({ widget }: LinkWidgetProps) => {
  const props: LinkStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const { classes } = useStyles(props);

  const [selected, setSelected] = useState<boolean>(false);
  const { selection } = useSelection();

  const ref = useRef<HTMLAnchorElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [selection, widget]);

  return (
    <div className={selected ? classes.selected : ''}>
      <div className={classes.propertySectionLabel}>
        <Link
          ref={ref}
          className={classes.style}
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
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
    </div>
  );
};
