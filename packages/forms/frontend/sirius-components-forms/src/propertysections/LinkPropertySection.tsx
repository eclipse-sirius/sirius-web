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
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import { LinkPropertySectionProps, LinkStyleProps } from './LinkPropertySection.types';

const useStyle = makeStyles<Theme, LinkStyleProps>(() => ({
  style: {
    color: ({ color }) => (color ? color : 'inherit'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
}));

/**
 * Defines the content of a Link property section.
 */
export const LinkPropertySection = ({ widget }: LinkPropertySectionProps) => {
  const props: LinkStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useStyle(props);

  return (
    <div>
      <Link className={classes.style} id={widget.id} href={widget.url} rel="noopener noreferrer" target="_blank">
        {widget.label}
      </Link>
    </div>
  );
};
