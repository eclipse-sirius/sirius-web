/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { useMutation } from '@apollo/client';
import { ServerContext, ServerContextValue, getCSSColor, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import gql from 'graphql-tag';
import { useContext, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { GQLButton } from '../form/FormEventFragments.types';
import { HelpTooltip } from '../propertysections/HelpTooltip';
import { getTextDecorationLineValue } from './../propertysections/getTextDecorationLineValue';
import {
  GQLErrorPayload,
  GQLPushButtonInput,
  GQLPushButtonMutationData,
  GQLPushButtonMutationVariables,
  GQLPushButtonPayload,
  GQLSuccessPayload,
  ToolbarActionProps,
  ToolbarActionStyleProps,
} from './ToolbarAction.types';

const useStyle = makeStyles<ToolbarActionStyleProps>()(
  (theme, { backgroundColor, foregroundColor, fontSize, italic, bold, underline, strikeThrough, iconOnly }) => ({
    style: {
      minWidth: '32px',
      lineHeight: 1.25,
      backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.light,
      color: foregroundColor ? getCSSColor(foregroundColor, theme) : 'white',
      fontSize: fontSize ? fontSize : undefined,
      fontStyle: italic ? 'italic' : undefined,
      fontWeight: bold ? 'bold' : undefined,
      textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
      '&:hover': {
        backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.main,
        color: foregroundColor ? getCSSColor(foregroundColor, theme) : 'white',
        fontSize: fontSize ? fontSize : undefined,
        fontStyle: italic ? 'italic' : undefined,
        fontWeight: bold ? 'bold' : undefined,
        textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
      },
    },
    icon: {
      marginRight: iconOnly ? theme.spacing(0) : theme.spacing(2),
    },
  })
);

export const pushButtonMutation = gql`
  mutation pushButton($input: PushButtonInput!) {
    pushButton(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLPushButtonPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLPushButtonPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

/**
 * Defines the content of a ToolbarAction.
 * The content is submitted when pushing the associated button.
 */
export const ToolbarAction = ({ editingContextId, formId, widget, readOnly }: ToolbarActionProps) => {
  const props: ToolbarActionStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
    iconOnly: widget.buttonLabel ? false : true,
  };
  const { classes } = useStyle(props);
  const { t } = useTranslation('sirius-components-forms');

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const [pushButton, { loading, data, error }] = useMutation<GQLPushButtonMutationData, GQLPushButtonMutationVariables>(
    pushButtonMutation
  );

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage(t('errors.unexpected'));
      }
      if (data) {
        const { pushButton } = data;
        if (isErrorPayload(pushButton) || isSuccessPayload(pushButton)) {
          addMessages(pushButton.messages);
        }
      }
    }
  }, [loading, error, data]);

  const onClick = () => {
    const input: GQLPushButtonInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      buttonId: widget.id,
    };
    const variables: GQLPushButtonMutationVariables = { input };
    pushButton({ variables });
  };

  const getImageURL = (widget: GQLButton) => {
    let imageURL: string = '';
    if (widget.imageURL) {
      if (widget.imageURL.startsWith('http://') || widget.imageURL.startsWith('https://')) {
        imageURL = widget.imageURL;
      }
      imageURL = httpOrigin + widget.imageURL;
    }
    return imageURL;
  };

  return (
    <div>
      <HelpTooltip editingContextId={editingContextId} formId={formId} widgetId={widget.id}>
        <Button
          data-testid={widget.buttonLabel}
          size="small"
          variant="contained"
          color="primary"
          onClick={onClick}
          disabled={readOnly || widget.readOnly}
          classes={{ root: classes.style }}>
          {widget.imageURL?.length ?? 0 > 0 ? (
            <img className={classes.icon} width="16" height="16" alt={widget.label} src={getImageURL(widget)} />
          ) : null}
          {widget.buttonLabel}
        </Button>
      </HelpTooltip>
    </div>
  );
};
