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
import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from 'tss-react/mui';
import { GQLContainerBorderStyle, GQLFlexDirection, GQLFlexWrap } from '../form/FormEventFragments.types';
import { FlexboxContainerPropertySectionProps } from './FlexboxContainerPropertySection.types';
import { PropertySection } from './PropertySection';
import { PropertySectionLabel } from './PropertySectionLabel';

const useFlexboxContainerPropertySectionStyles = makeStyles<{
  flexDirection: GQLFlexDirection;
  flexWrap: GQLFlexWrap;
  flexGrow: number;
  borderStyle: GQLContainerBorderStyle;
}>()((theme, { flexDirection, flexWrap, flexGrow, borderStyle }) => ({
  containerAndLabel: {
    margin: borderStyle ? theme.spacing(0.5) : 0,
    padding: borderStyle ? theme.spacing(0.5) : 0,
    borderWidth: borderStyle?.size || 0,
    borderColor: getCSSColor(borderStyle?.color, theme) || 'transparent',
    borderStyle: borderStyle?.lineStyle || 'solid',
    borderRadius: borderStyle?.radius || 0,
  },
  container: {
    display: 'flex',
    flexWrap: flexWrap,
    flexDirection: flexDirection,
    '& > *': {
      marginBottom: theme.spacing(0),
    },
  },
  children: {
    flexGrow: flexGrow,
  },
}));

export const FlexboxContainerPropertySection = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: FlexboxContainerPropertySectionProps) => {
  const { classes } = useFlexboxContainerPropertySectionStyles({
    flexDirection: widget.flexDirection,
    flexWrap: widget.flexWrap,
    flexGrow: widget.flexGrow,
    borderStyle: widget.borderStyle,
  });

  let children = widget.children.map((childWidget) => (
    <div className={classes.children} key={childWidget.id}>
      <PropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={childWidget}
        readOnly={readOnly || widget.readOnly}
        key={childWidget.id}
      />
    </div>
  ));

  return (
    <div className={classes.containerAndLabel} data-testid={`flexbox-${widget.label}`}>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      <div className={classes.container}>{children}</div>
    </div>
  );
};
