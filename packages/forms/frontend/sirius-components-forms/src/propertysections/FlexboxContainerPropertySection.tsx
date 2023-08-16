/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { makeStyles, Theme } from '@material-ui/core/styles';
import {
  FlexboxContainerPropertySectionProps,
  FlexboxContainerPropertySectionStyleProps,
} from './FlexboxContainerPropertySection.types';
import { PropertySection } from './PropertySection';
import { PropertySectionLabel } from './PropertySectionLabel';

const useFlexboxContainerPropertySectionStyles = makeStyles<Theme, FlexboxContainerPropertySectionStyleProps>(
  (theme) => ({
    containerAndLabel: {
      borderWidth: ({ borderStyle }) => borderStyle?.size || 0,
      borderColor: ({ borderStyle }) => getCSSColor(borderStyle?.color, theme) || 'transparent',
      borderStyle: ({ borderStyle }) => borderStyle?.lineStyle || 'solid',
      borderRadius: ({ borderStyle }) => borderStyle?.radius || 0,
      margin: ({ margin }) => margin || null,
      padding: ({ padding }) => padding || null,
    },
    container: {
      display: 'flex',
      flexWrap: ({ flexWrap }) => flexWrap,
      flexDirection: ({ flexDirection }) => flexDirection,
      gap: ({ gap }) => gap || null,
      alignItems: ({ alignItems }) => alignItems,
      justifyContent: ({ justifyContent }) => justifyContent,
      '& > *': {
        marginBottom: theme.spacing(0),
      },
    },
    children: {
      flexGrow: ({ flexGrow }) => flexGrow,
    },
  })
);

export const FlexboxContainerPropertySection = ({
  editingContextId,
  formId,
  widget,
  widgetSubscriptions,
  setSelection,
  readOnly,
}: FlexboxContainerPropertySectionProps) => {
  const classes = useFlexboxContainerPropertySectionStyles({
    flexDirection: widget.flexDirection,
    flexWrap: widget.flexWrap,
    flexGrow: widget.flexGrow,
    borderStyle: widget.borderStyle,
    justifyContent: widget.justifyContent,
    alignItems: widget.alignItems,
    margin: widget.margin,
    padding: widget.padding,
    gap: widget.gap,
  });

  let children = widget.children.map((childWidget) => (
    <div className={classes.children} key={childWidget.id}>
      <PropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={childWidget}
        widgetSubscriptions={widgetSubscriptions}
        setSelection={setSelection}
        readOnly={readOnly || widget.readOnly}
        key={childWidget.id}
      />
    </div>
  ));

  return (
    <div className={classes.containerAndLabel} data-testid={`flexbox-${widget.label}`}>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} subscribers={[]} />
      <div className={classes.container}>{children}</div>
    </div>
  );
};
